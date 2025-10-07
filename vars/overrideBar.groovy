 def call(String envName) {
    echo "Overriding BAR file using ibmint for environment: ${envName}"

    // Load the correct override file from the shared lib
    def overrideFileContent = libraryResource "overrides/${envName}-override.txt"

    // Write the content into the workspace
    writeFile file: "${env.WORKSPACE}/${envName}-override.txt", text: overrideFileContent

    // Run ibmint command inside Docker
    sh """
        rm -rf ${envName}-override.bar || true
        docker run --rm \
        --volumes-from jenkins \
        -w ${env.WORKSPACE} \
        ibmint:latest apply overrides ${env.WORKSPACE}/${envName}-override.txt \
        --input-bar-file ${env.WORKSPACE}/${name}.bar \
        --output-bar-file ${env.WORKSPACE}/${envName}-override.bar
    """

    echo "BAR file override complete for ${envName}."
}

