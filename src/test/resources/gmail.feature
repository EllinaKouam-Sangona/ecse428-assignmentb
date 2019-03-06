Feature: Gmail
  
Scenario: Attaching file through the file system

	Given I am a gmail user
	And I am logged in
	And I am on the new email page
	When I select the attach files button
	And select the file to attach in my file explorer
	And I click “open” on the file explorer
	Then the file is included in my email
	And file icon appears in my email


Scenario: Attaching a file by dragging it 

	Given I am a gmail user 
	And I am logged in
	And I am on the new email page 
	When I select a file from my computer 
	And drag it inside the email page
	Then the file is included in my email
	And file icon appears in my email

Scenario: Not including an image 

	Given I am a gmail user 
	And I am logged in
	And I am on the new email page 
	When I select the attach files button
	And I click “open” on the file explorer
	Then the file explorer should prompt me to select a file

Scenario: Exceeding the maximum file size limit  
	
	Given I am a gmail user
	And I am logged in
	And I am on the new email page
	When I select the attach files button
	And select the file to attach in my file explorer
	And I click “open” on the file explorer
	Then a warning saying the chosen file size is too big should be displayed
