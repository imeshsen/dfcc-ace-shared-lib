// def call() {
//     echo "Overriding BAR file using ibmint to ${env.WORKSPACE}..."
//                 sh """
//                     docker run --rm \\
//                     --volumes-from jenkins \\
//                     ibmint:latest apply overrides \\
//                     --input-bar-file ${env.WORKSPACE}/MyIntegrationTestProject.bar \\
//                     --overrides-file dev-overrides.yaml
//                     --output-bar-file ${env.WORKSPACE}/override.bar
//                 """
//                 echo "BAR file override complete."
// }


// docker run --rm -v /home/imesh/myStuff/ibmovride:/app ibmint:latest apply overrides /app/overrides.txt --input-bar-file /app/MyIntegrationTestProject.bar \\
// --output-bar-file /app/MyIntegrationTestProject_updated.bar


def call(String envName) {
    echo "Overriding BAR file using ibmint for environment: ${envName}"

    // Load the correct override file from the shared lib
    def overrideFileContent = libraryResource "overrides/${envName}-override.yaml"

    // Write the content into the workspace
    writeFile file: "${env.WORKSPACE}/${envName}-override.yaml", text: overrideFileContent

    // Run ibmint command inside Docker
    sh """
        docker run --rm \
        -v ${env.WORKSPACE}:${env.WORKSPACE} \
        -w ${env.WORKSPACE} \
        ibmint:latest apply overrides \
        --input-bar-file ${env.WORKSPACE}/MyIntegrationTestProject.bar \
        --overrides-file ${envName}-override.txt \
        --output-bar-file ${env.WORKSPACE}/${envName}-override.bar
    """

    echo "BAR file override complete for ${envName}."
}

