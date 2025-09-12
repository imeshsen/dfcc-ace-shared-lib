def call(String environment) {
    // Read the environment-specific configuration from the resources folder
    def config = readJSON(text: libraryResource("${environment}.json"))
    echo "Deploying BAR file to ${environment} environment..."
    sh """
        docker run --rm \\
        --volumes-from jenkins \\
        --network ace-network \\
        ibm-ace-container:latest deploy \\
        --input-bar-file /var/jenkins_home/workspace/dfcc-demo/MyIntegrationTestProject.bar \\
        --output-host ${config.host} \\
        --output-port ${config.port} \\
        --output-server ${config.server}
    """
    echo "BAR file deployment initiated successfully."
}
