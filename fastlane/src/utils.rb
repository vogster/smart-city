require_relative "build_class"


#***************************************************************************************************
# Gradle
# Function return "versionName"
def getVersionName()
    get_version_name(
        ext_constant_name: "versionName"
    )
end

# Function return "versionCode"
def getVersionCode()
    get_version_name(
        ext_constant_name: "versionCode"
    )
end

def buildProject(buildData)
    buildType = buildData.getBuildType.capitalize()
    flavour = buildData.getFlavour.capitalize()

    task = "--no-daemon assemble" + flavour + buildType + " -info --max-workers=2"
    gradle(task: task)
end
#***************************************************************************************************


#***************************************************************************************************
# Jira
# Function return text with number and name of tasks
def getJiraNotesText(buildData)
    versionName = buildData.getVersionName()
    versionCode = buildData.getVersionCode()
    jiraProjectName = buildData.getJiraProjectName()

    jira_release_notes(
        version: "a#{versionName}(#{versionCode})",
        project: jiraProjectName
    )
end

# Function return text in html format
def getJiraNotesHtml(buildData)
    versionName = buildData.getVersionName()
    versionCode = buildData.getVersionCode()
    jiraProjectName = buildData.getJiraProjectName()

    jiraNotes = jira_release_notes(
        version: "a#{versionName}(#{versionCode})",
        project: jiraProjectName,
        format: "none"
    )

    renderer = ReleaseNotesRenderer.new(
        versionName,
        ENV["FL_JIRA_SITE"],
        jiraNotes,
        "Firebase build"
    )

    renderer.render_mail
end

# Function return text in markdown format
def getJiraNotesMarkdown(buildData)
    versionName = buildData.getVersionName()
    versionCode = buildData.getVersionCode()
    jiraProjectName = buildData.getJiraProjectName()

    jiraNotes = jira_release_notes(
        version: "a#{versionName}(#{versionCode})",
        project: jiraProjectName,
        format: "none"
    )

    jiraNotes.each { |x|
        x.fields['summary'].gsub! "*", ""
        x.fields['summary'].gsub! "+", ""
        x.fields['summary'].gsub! "_", ""
        x.fields['summary'].gsub! "~", ""
        x.fields['summary'].gsub! "`", ""
        x.fields['summary'].gsub! "[", ""
        x.fields['summary'].gsub! "]", ""
    }

    renderer = ReleaseNotesRenderer.new(
        versionName,
        ENV["FL_JIRA_SITE"],
        jiraNotes,
        "Firebase build"
    )

    renderer.render_telegram
end
#***************************************************************************************************


#***************************************************************************************************
# Firebase
# Function uploading APK to Firebase App Distribution
def uploadToFirebase(buildData)
    flavor = buildData.getFlavour().capitalize()
    buildType = buildData.getBuildType().capitalize()
    serverUrl = getServerUrl(buildData)

    jiraNotes = getJiraNotesText(buildData)

    message = flavor + " " + buildType + " Build\n\n" +
        "Base url: " + serverUrl + "\n\n" +
        "Jira tasks:\n" + jiraNotes

    firebaseAppId = buildData.getFirebaseAppId()
    credentialPath = "firebase/credential.json"

    firebase_app_distribution(
        app: firebaseAppId,
        service_credentials_file: credentialPath,
        release_notes: message,
        groups: "internal-qa-group"
    )
end
#***************************************************************************************************


#***************************************************************************************************
# Gmail
# Function send gmail notification to recipients
def sendGmail(buildData)
    flavor = buildData.getFlavour().capitalize()
    versionName = buildData.getVersionName()
    versionCode = buildData.getVersionCode()

    projectName = buildData.getProjectName()
    jiraProjectName = buildData.getJiraProjectName()

    subjectGmail = "[Android][#{jiraProjectName}] " +
        "#{projectName} " +
        "#{versionName}(#{versionCode})"

    jiraNotes = getJiraNotesHtml(buildData)

    message = "Available new <b>#{flavor}</b> build in:<br>" +
              "<b>Firebase</b> App Distribution<br>" +
              jiraNotes

    buildType = buildData.getBuildType()
    recipients = File.read("../gmail/recipients.txt").split("\n")

    gmail(
        to: recipients,
        subject: subjectGmail,
        body: message
    )
end
#***************************************************************************************************


#***************************************************************************************************
# Telegram
# Function sending message to Telegram
def sendTelegram(buildData)
    flavor = buildData.getFlavour().capitalize()
    versionName = buildData.getVersionName()
    versionCode = buildData.getVersionCode()
    projectName = buildData.getProjectName()
    serverUrl = getServerUrl(buildData)

    jiraProjectName = buildData.getJiraProjectName()
    firebaseUrl = buildData.getAppDistributionUrl()
    jiraNotes = getJiraNotesMarkdown(buildData)

    flavorSmiles = getFlavourSmiles(buildData)

    message = "🚀 [[#{jiraProjectName}]] *#{projectName} #{versionName} (#{versionCode})* 🚀" +
        "\n\n" +
        "Available new *#{flavor}* build in:\n" +
        "[Firebase App Distribution](#{firebaseUrl})" + "\n\n" +
        "*Stage*: " + flavor + " " + flavorSmiles + "\n" +
        "*Server url*: " + serverUrl + "\n\n" +
        "*Jira tasks:*" + jiraNotes

    puts message

    telegram(
        token: ENV["TG_BOT_TOKEN"],
        chat_id: ENV["TG_CHAT_ID"],
        text: message,
        parse_mode: "markdown"
    )
end
#***************************************************************************************************

#***************************************************************************************************
# General
# Function return base url by flavor name
def getServerUrl(buildData)
    if buildData.isDevelop()
        return ENV["URL_DEVELOP"]
    else
        return ENV["URL_QA"]
    end
end

def getFlavourSmiles(buildData)
    if buildData.isDevelop()
        return "⚙️"
    else
        return "🔎"
    end
end
#***************************************************************************************************