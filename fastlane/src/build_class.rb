class BuildData

    #***********************************************************************************************
    # Constants
    DEBUG = "debug"
    RELEASE = "release"

    DEVELOP = "develop"
    QA = "qa"
    PRODUCTION = "production"

    #Project name
    def setProjectName(projectName)
        @projectName = projectName
    end

    def getProjectName()
        return @projectName
    end
    #***********************************************************************************************


    #***********************************************************************************************
    # Build type
    def setDebugType()
        @buildType = DEBUG
    end

    def setReleaseType()
        @buildType = RELEASE
    end

    def getBuildType()
        return @buildType
    end

    def isDebug()
        return @buildType == DEBUG
    end

    def isRelease()
        return @buildType == RELEASE
    end
    #***********************************************************************************************


    #***********************************************************************************************
    # Flavours
    def setDevelopFlavour()
        @flavour = DEVELOP
    end

    def setQaFlavour()
        @flavour = QA
    end

    def setProductionFlavour()
        @flavour = PRODUCTION
    end

    def getFlavour()
        return @flavour
    end

    def isDevelop()
        return @flavour == DEVELOP
    end

    def isQa()
        return @flavour == DEVELOP
    end

    def isProduction()
        return @flavour == PRODUCTION
    end
    #***********************************************************************************************


    #***********************************************************************************************
    # VersionName
    def setVersionName(versionName)
        @versionName = versionName
    end

    def getVersionName()
        return @versionName
    end
    #***********************************************************************************************


    #***********************************************************************************************
    #Version Code
    def setVersionCode(versionCode)
        @versionCode = versionCode
    end

    def getVersionCode()
        return @versionCode
    end
    #***********************************************************************************************


    #***********************************************************************************************
    # Jira project name
    def setJiraProjectName(jiraProjectName)
        @jiraProjectName = jiraProjectName
    end

    def getJiraProjectName()
        return @jiraProjectName
    end
    #***********************************************************************************************


    #***********************************************************************************************
    # Firebase app id
    def setFirebaseAppId(firebaseAppId)
        @firebaseAppId = firebaseAppId
    end

    def getFirebaseAppId()
        return @firebaseAppId
    end
    #***********************************************************************************************


    #***********************************************************************************************
    # Firebase url app distribution
    def setAppDistributionUrl(appDistributionUrl)
        @appDistributionUrl = appDistributionUrl
    end

    def getAppDistributionUrl()
        return @appDistributionUrl
    end
    #***********************************************************************************************
end