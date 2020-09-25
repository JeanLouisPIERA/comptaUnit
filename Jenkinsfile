pipeline {

		agent {
	        label 'master'
	    }
	
		triggers {
        	pollSCM('0-59/1 * * * *')
    	}
    	
		stages{
	        stage("Compile the source code")	{
	            steps	{
	            bat "mvnw compile"
	            }
	        }
	        stage("Test the source code")	{
	            steps	{
	            bat "mvnw test"
	            }
	        }
	         stage("Code coverage. Limiting the minimum score for lines coverage to 75%")	{
	            steps	{
	            bat "mvnw test jacoco:report"
	            publishHTML	(target:	[
					reportDir:	'target/site/jacoco',
					reportFiles:	'index.html',
					reportName:	"Code coverage report"
				])
	            bat "./mvnw clean verify"
	            
	            }
	        }
			stage("Package the application")	{
		            steps	{
		            bat "mvnw clean package -DskipTests"
		            }
		        }
	
	    }
	        
	        
	 } 