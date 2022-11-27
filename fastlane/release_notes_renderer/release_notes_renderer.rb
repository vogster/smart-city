require 'erb'
require "cgi"

# http://blog.revathskumar.com/2014/10/ruby-rendering-erb-template.html
class ReleaseNotesRenderer
  def initialize(version, base_url, issues, build_type)
    @groups = issues.group_by { |i| i.fields['issuetype']['name'] }
    @base_url = base_url
    @version = version
    @build_type = build_type

    templates_folder = 'release_notes_renderer/templates/'
    @mail_template = File.read("#{templates_folder}/mail_template.html.erb")
    @release_notes_template = File.read("#{templates_folder}/release_notes_template.txt.erb")
    @telegram_template = File.read("#{templates_folder}/telegram_template.txt.erb")
  end

  def render_mail
    ERB.new(@mail_template, 0, '>').result(binding)
  end

  def render_release_notes
    ERB.new(@release_notes_template, 0, '>').result(binding)
  end

  def render_telegram
    ERB.new(@telegram_template, 0, '>').result(binding)
  end
end