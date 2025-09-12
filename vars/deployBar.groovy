// vars/deployBar.groovy
import groovy.json.JsonSlurper
import org.jenkinsci.plugins.workflow.steps.NonCPS

def call(String environment) {
    def config = loadConfig(environment)

    echo "Deploying BAR file to ${environment} environment..."

    sh """
        docker run --rm \\
        --volumes-from jenkins \\
        --network ace-network \\
        ibmint:latest deploy \\
        --input-bar-file /var/jenkins_home/workspace/dfcc-demo/MyIntegrationTestProject.bar \\
        --output-host ${config['host']} \\
        --output-port ${config['port']} \\
        --output-server ${config['server']}
    """

    echo "BAR file deployment initiated successfully."
}

@NonCPS
def loadConfig(String environment) {
    def jsonText = libraryResource("${environment}.json")
    def parsed = new JsonSlurper().parseText(jsonText)
    // Convert LazyMap → HashMap explicitly
    return new HashMap(parsed)
}
