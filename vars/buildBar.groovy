
def call() {
    echo "Building BAR file using ibmint to echo  ${env.WORKSPACE}..."
                sh """
                    docker run --rm \\
                    --volumes-from jenkins \\
                    ibmint:latest package \\
                    --input-path ${env.WORKSPACE} \\
                    --output-bar-file ${env.WORKSPACE}/MyIntegrationTestProject.bar \\
                    --do-not-compile-java
                """
                echo "BAR file built."
}
