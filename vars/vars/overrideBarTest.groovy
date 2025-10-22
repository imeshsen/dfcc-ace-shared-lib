sh """
    docker run --rm \
    -v ${env.WORKSPACE}:${env.WORKSPACE} \
    -w ${env.WORKSPACE} \
    ibmint:latest \
    ibmint apply overrides \
    --assembly-archive ${env.WORKSPACE}/MyIntegrationTestProject.bar \
    --properties-file ${envName}-override.txt \
    --output-bar ${env.WORKSPACE}/${envName}-override.bar
"""
