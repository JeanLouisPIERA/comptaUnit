pipeline {

		agent {
	        label 'master'
	    }
	
		triggers {
        	pollSCM('0-59/1 * * * *')
    	}
    	
		stages{
		
			stage("Sonar Analysis") {
			     steps {
			          
			          withSonarQubeEnv(sonarqubeEnvName) {
			          bat "mvn -B -U -Dproject.version=${version} " +
			                              (env.BRANCH_NAME != 'master' ? "-Dsonar.branch.name=${env.BRANCH_NAME} " : "") +
			                              " -Dsonar.value.url=${sonarqubeserver}" +
			                              " sonar:sonar"
					          }
					     }
					}
		
	        stage("Compile the source code")	{
	            steps	{
	            bat "mvn compile"
	            }
	        }
	        stage("Test the source code")	{
	            steps	{
	            bat "mvn test-compile  "
	            
	            
	            }
	        }
	        
	         stage("Code coverage. Limiting the minimum score for lines coverage to 75%")	{
	            steps	{
	            bat " mvn test jacoco:check jacoco:report -debug "
	      
	            publishHTML	(target:	[
				allowMissing: false,
			    alwaysLinkToLastBuild: true,
		        keepAll: true,
		        reportDir: 'myerp-business/target/site/jacoco',
				reportFiles:	'index.html',
				reportName:	"myerp-business coverage report"
			])
	            bat "mvn clean verify"
	            }
	            post {
	                always {
	                    junit '**/target/surefire-reports/*.xml'
	                    step( [ 
						  $class: 'JacocoPublisher'
						])
	                    }
	            	}
	        }
			stage("Package the application")	{
		            steps	{
		            bat "mvn clean package -DskipTests"
		            }
		        }
	
	    }
	        
	        
	 } 