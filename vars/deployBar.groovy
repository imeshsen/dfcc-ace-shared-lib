// vars/deployBar.groovy
import groovy.json.JsonSlurper
import com.cloudbees.groovy.cps.NonCPS

def call(String environment) {
    def config = loadConfig(environment)

    echo "Deploying BAR file to ${environment} environment..."

    sh """
        docker run --rm \\
        --volumes-from jenkins \\
        ibmint:latest deploy \\
        --input-bar-file ${env.WORKSPACE}/${name}-override.bar \\
        --output-host ${config['host']} \\
        --output-port ${config['port']} \\
        --output-server ${config['server']}
    """

    echo "BAR file deployment initiated successfully."
}

@NonCPS
def loadConfig(String environment) {
    def jsonText = libraryResource("environments/${environment}.json")
    def parsed = new JsonSlurper().parseText(jsonText)
    // Convert LazyMap → HashMap explicitly
    return new HashMap(parsed)
}
