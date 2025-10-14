 def call(String envName) {

  def name = sh(
        script: "git config --get remote.origin.url | sed 's#.*/##' | sed 's/\\.git\$//'",
        returnStdout: true
    ).trim()
  
    echo "Overriding ${name}.bar to ${name}-${envName}-override.bar using ibmint for environment: ${envName}"

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
        --output-bar-file ${env.WORKSPACE}/${name}-${envName}-override.bar
    """

    echo "BAR file override complete for ${envName}."
}

