#!groovy

@Library("Infrastructure")

def type = "java"
def product = "pdda"
def component = "public-display-manager"
def secrets = [
  'pdda-${env}': [
    secret('public-display-data-aggregator-POSTGRES-USER', 'DB_USER_NAME'),
    secret('public-display-data-aggregator-POSTGRES-PASS', 'DB_PASSWORD'),
    secret('public-display-data-aggregator-POSTGRES-DATABASE', 'DB_NAME'),
    secret('public-display-data-aggregator-POSTGRES-HOST', 'DB_HOST'),
    secret('public-display-data-aggregator-POSTGRES-PORT', 'DB_PORT'),
    secret('public-display-data-aggregator-POSTGRES-SCHEMA', 'DB_SCHEMA'),
    secret('public-display-data-aggregator-authentication-clientID', 'AZURE_CLIENT_ID'),
    secret('public-display-data-aggregator-authentication-clientSecret', 'AZURE_CLIENT_SECRET'),
    secret('public-display-data-aggregator-authentication-tenantID', 'AZURE_TENANT_ID')
  ],
]

withPipeline(type, product, component) {
  loadVaultSecrets(secrets)

  afterAlways('smokeTest:stg') {
    steps.archiveArtifacts allowEmptyArchive: true, artifacts: 'smoke-test-report/**/*'
  }
}

static LinkedHashMap<String, Object> secret(String secretName, String envVar) {
    [ $class: 'AzureKeyVaultSecret',
        secretType: 'Secret',
        name: secretName,
        version: '',
        envVariable: envVar
    ]
}
