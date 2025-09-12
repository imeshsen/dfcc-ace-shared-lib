// A custom step to deploy the BAR file to a specified environment
def call(String environment) {
    // Read the file content from the shared library's 'resources' folder
    def jsonText = libraryResource("${environment}.json")

    // Parse the JSON string into a Groovy object
    def config = new groovy.json.JsonSlurper().parseText(jsonText)

    echo "Deploying BAR file to ${environment} environment..."
    sh """
        docker run --rm \\
        --volumes-from jenkins \\
        --network ace-network \\
        ibmint:latest deploy \\
        --input-bar-file /var/jenkins_home/workspace/dfcc-demo/MyIntegrationTestProject.bar \\
        --output-host ${config.host} \\
        --output-port ${config.port} \\
        --output-server ${config.server}
    """
    echo "BAR file deployment initiated successfully."
}