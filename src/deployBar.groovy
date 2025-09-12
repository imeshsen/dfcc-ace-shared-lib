// A custom step to deploy the BAR file to a specified environment
def call(String environment) {
    // Read the environment-specific configuration from the resources folder
    def config = readJSON(file: "resources/${environment}.json")

    echo "Deploying BAR file to ${environment} environment..."
    sh """
        docker run --rm \\
        --volumes-from jenkins \\
        --network ace-network \\
        ibmint:latest deploy \\
        --input-bar-file /var/jenkins_home/workspace/shared-lib-test/MyIntegrationTestProject.bar \\
        --output-host ${config.host} \\
        --output-port ${config.port} \\
        --output-server ${config.server}
    """
    echo "BAR file deployment initiated successfully."
}