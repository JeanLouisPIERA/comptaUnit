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
	            bat "mvn compile"
	            }
	        }
	        stage("Test the source code")	{
	            steps	{
	            bat "mvn test"
	            
	            }
	        }
	        
	         stage("Code coverage. Limiting the minimum score for lines coverage to 75%")	{
	            steps	{
	            bat "mvn jacoco:pre-unit-test jacoco:check test jacoco:post-unit-test -debug "
	            publishCoverage	adapters:[jacocoAdapter('**/target/coverage-reports/jacoco-ut.exec')], sourceFileResolver: sourceFiles('STORE_ALL_BUILD')
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