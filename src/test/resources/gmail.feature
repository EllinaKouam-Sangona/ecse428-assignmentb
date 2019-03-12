Feature: Gmail
  
Scenario Outline: Attaching file through the file system

	Given I am on the new email page
	When I enter <recipient> and the email body
	And I select the attach files button
	And select the file to attach in my file explorer, <image>
	Then the file <image> is included in my email 
	And the email is sent

Examples:
| recipient                | image |
| ellinabrina@hotmail.com | Image.png |
| ellina.kouam.sangona@gmail.com     | red.png |
| ellina.kouam-sangona@mail.mcgill.ca | red.png|
| ellinakouam@gmail.com | red.png |
| ellinabrina@hotmail.com    | Image.png |

Scenario Outline: Attaching a file by dragging it 

	Given I am on the new email page
	When I drag a file from my computer into the email page, <image>
	Then the file <image> is included in my email
	And the email is sent to <recipient> with my file
	
Examples:
| recipient                | image |
| ellinabrina@hotmail.com | Image.png |
| ellina.kouam.sangona@gmail.com     | red.png |
| ellina.kouam-sangona@mail.mcgill.ca | red.png|
| ellinakouam@gmail.com | red.png |
| ellinabrina@hotmail.com    | Image.png |
	
Scenario Outline: Exceeding the maximum file size limit  
	
	Given I am on the new email page
	When I select the attach file button
	And select the file to attach in my file explorer, <image>
	Then a prompt saying Your file is larger than 25 MB appears
	And the email is sent to <recipient> with my file

Examples:
| recipient                | image |
| ellina.kouam.sangona@gmail.com  | largeImage.jpg |
| ellina.kouam.sangona@gmail.com     | largeImage2.jpg |
| ellina.kouam.sangona@gmail.com | largeImage3.jpg |
| ellina.kouam.sangona@gmail.com     | largeImage4.jpg |
| ellina.kouam.sangona@gmail.com | largeImage5.jpg |

Scenario Outline: Not including an image 

	Given I am on the new email page
	When I enter <recipient> and the email body
	And I select the attach files button
	And I click open on the file explorer
	Then the open button should be non interactable

Examples:
| recipient               
| ellinabrina@hotmail.com 
| ellina.kouam.sangona@gmail.com     
| ellina.kouam-sangona@mail.mcgill.ca 
| ellinakouam@gmail.com 
| ellinabrina@hotmail.com    
