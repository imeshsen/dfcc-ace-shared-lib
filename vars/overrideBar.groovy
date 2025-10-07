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


// docker run --rm -v /home/imesh/myStuff/ibmovride:/app ibmint:latest apply overrides /app/overrides.txt --input-bar-file /app/MyIntegrationTestProject.bar \\
// --output-bar-file /app/MyIntegrationTestProject_updated.bar
