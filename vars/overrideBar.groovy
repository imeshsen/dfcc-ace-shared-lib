 def call(String envName) {
    echo "Overriding BAR file using ibmint for environment: ${envName}"

    // Load the correct override file from the shared lib
    def overrideFileContent = libraryResource "overrides/${envName}-override.txt"

    // Write the content into the workspace
    writeFile file: "${env.WORKSPACE}/${envName}-override.txt", text: overrideFileContent

    // Run ibmint command inside Docker
    sh """
        docker run --rm \
        --volumes-from jenkins \
        ibmint:latest apply overrides ${envName}-override.txt \
        --input-bar-file ${env.WORKSPACE}/${name}.bar \
        --output-bar-file ${env.WORKSPACE}/${envName}-override.bar
    """

    echo "BAR file override complete for ${envName}."
}


        // -v ${env.WORKSPACE}:${env.WORKSPACE} \
        // -w ${env.WORKSPACE} \
