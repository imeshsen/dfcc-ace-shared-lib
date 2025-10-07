def call() {
    echo "Overriding BAR file using ibmint to ${env.WORKSPACE}..."
                sh """
                    docker run --rm \\
                    --volumes-from jenkins \\
                    ibmint:latest apply overrides \\
                    --input-bar-file ${env.WORKSPACE}/MyIntegrationTestProject.bar \\
                    --overrides-file dev-overrides.yaml
                    --output-bar-file ${env.WORKSPACE}/override.bar
                """
                echo "BAR file override complete."
}
