// vars/deployBar.groovy
import groovy.json.JsonSlurper
import com.cloudbees.groovy.cps.NonCPS

def call(String environment) {
    def config = loadConfig(environment)

    def name = sh(
        script: "git config --get remote.origin.url | sed 's#.*/##' | sed 's/\\.git\$//'",
        returnStdout: true
    ).trim()

    echo "Deploying ${name}-${envName}-override.bar file to ${environment} environment..."

    sh """
        docker run --rm \\
        --volumes-from jenkins \\
        ibmint:latest deploy \\
        --input-bar-file ${env.WORKSPACE}/${name}-${envName}-override.bar \\
        --output-host ${config['host']} \\
        --output-port ${config['port']} \\
        --output-server ${config['server']}
    """

    echo "${name}-${envName}-override.bar deployment completed successfully."
}

@NonCPS
def loadConfig(String environment) {
    def jsonText = libraryResource("environments/${environment}.json")
    def parsed = new JsonSlurper().parseText(jsonText)
    // Convert LazyMap → HashMap explicitly
    return new HashMap(parsed)
}
