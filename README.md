# ANDIE - A Non-Destructive Image Editor

ANDIE is a fast, lightweight and reliable editor for making basic image modifications

> ANDIE is still in development, so new features are still being added and bugs are still being resolved!

It is also non-destructive, in the sense that it can save an image, a set of image operations and edited version of the image separately

ANDIE's automatically generated Javadoc documentation can be seen [here](https://cosc202-team-k.cspages.otago.ac.nz/andie/docs/)

Examples of ANDIE can be seen below
![A screenshot of an image in ANDIE](/assets/andieexample.jpg "An unedited image open in ANDIE")
![A screenshot of an image that has been greyscaled in ANDIE](/assets/andieexamplebw.jpg "An image that has been filtered in ANDIE")
  
  
(Example image retrieved from [here](https://lovepik.com/image-500447536/simple.html))

## User guide

### Overview
ANDIE is designed to allow for fast, straightforward usage with helpful warnings to guide users away from potentially damaging actions.
The interface consists of eight submenus and an image representing the state of the edited image.
To interact with one of these submenus, simply click on the relevant header at the top of the screen. You will then be
presented with various options, which are outlined in the *submenus* guide below.
There is also a mini drawing toolbar with functions such as a pencil and the ability to crop shapes out of the image

## Submenus


  
### File
> Provides actions related to opening, closing and saving files
##### Open
Opens an image
If the image has previously been edited and saved in ANDIE, then the edits will appear 
##### Save
Saves an image and any image operations applied in separate files
##### Save a Copy
Saves an image and any image operations applied in separate files at a new location/name
##### Export a Copy
Saves the edited version of an image to some given location
##### Exit
Closes ANDIE  
  
    

### Edit
> Provides several fundamental editing actions
##### Undo
Undoes the most recent ImageOperation applied
##### Redo
Redoes the most recent ImageOperation that was undone
##### Resize
Resizes an image according to a scale retrieved from the user
The scale must be between 0 and a displayed limit based on the current size of the image  

### Rotate
##### Rotate 90° Right
Turns image 90 degrees to the right
##### Rotate 90° Left
Turns image 90 degrees to the left
##### Rotate 180° 
Turns the image 180 degrees so it is upside down, but not vertically flipped


### Flip
##### Flip Horizontally
Flips image using a horizontal reference line (axis)
##### Flip Vertically
Flips image using a vertical reference line (axis)
    
      
### View
> Lets the user customise their ANDIE experience 
##### Zoom In
Increases zoom by 10%
##### Zoom Out
Decreases zoom by 10%
##### Zoom to Fit Window Size
Zooms in/out of the image so that it fills ANDIE's current window
##### Zoom Full
Resets the zoom to the image's actual size
##### Resize Window to Image Size
Resizes ANDIE's window to match the current image size  
  
    

### Filter
> Provides a variety of filters for images
##### Sharpen 
Applies a sharpening filter to the image
##### Median Filter
Applies a median filter to the image
##### Mean Filter
Applies a mean filter to the image
#### Soft Blur
##### Gaussian Filter
Applies a Gaussian blur filter to the image
Uses a slider to adjust the severity of the blur
##### Soft Blur
Applies a soft blur filter to the image
#### Emboss Filter
Applies an Emboss Filter to the image
8 Different Emboss Filter modes are available
#### Sobel Filter 
Applies a Sobel Filter to the image
Both horizontal and vertical Sobel Filters are available
  
    

### Colour
> Provides options to adjust the colouring of images
##### Greyscale
Converts the image to black and white (greyscale)
##### Brightness and Contrast
Provides options to adjust image brightness and contrast  

### Help
> Provides the options to change language or to view documentation
##### Language
Pop up menu to change language
##### Help
Pop up window with Documentation for each menu

## Other features

### Drawing toolbar
#### Pencil
Allows the user to draw on an image with a 'pencil'
Note that the zoom and window size are automatically adjusted when the pencil is enabled
#### Colour selector
Lets the user select a colour for drawing actions
#### Selector tool
Lets the user select and crop regions using any of several shapes

## How code was tested
Each person tested the features they developed independently, and then later tested each other's features.

Tests included basic end-to-end testing to ensure filters worked as intended. During these tests, both usability
and functionality was tested and the code adjusted as needed.

In addition, some tests were run to test the code's exception handling abilities. In these cases, the testers
carried out processes 'designed to fail' such as importing corrupt image files, selecting filters where no image
was present and attempting to resize an image with a negative scale. If it was found that the program crashed
in one of these situations then the tester would add any necessary exception handling or communicate the issue with the rest of
the group.


## Who did what

### Assigning tasks
Niamh segregated part 1's 12 tasks into groups based on functional areas which were adjusted by other team members as needed. 
There were six groups. Each person took on one of the groups and the tasks in the remaining group (exception handling and other error avoidance/prevention) were assigned to everyone. The tasks were recorded in a Google Sheet created by Niamh [See here](https://docs.google.com/spreadsheets/d/1wUH_0vrxPMKSQxfkcnS12u-eqmQrrh8pfllngzK0Vwk/edit#gid=0) which was used to track the progress of each task.  

For part 2, team members assigned themselves to tasks, primarily based on the work done in part 1

### Communicating throughout the project
The team tended to meet in-person at COSC202 labs where much of the progress was discussed and everyone could help to keep one another on track. Outside of these, the team communicated using a Discord server set up by Isaac. Though most discussion occurred within the server's general chat, Isaac also posted regular updates on the team's progress to a separate channel and set up another channel to automatically alert people of changes to the Google Sheet.
![Discord Server](/assets/discordserver.png "A Screenshot of the Discord Server")
![Google Sheets](/assets/googlesheets.png "A Screenshot of the Sheets document")

### Individual contributions

#### Niamh
- Set up the Google Sheet to track team progress
- Grouped tasks into functional areas
- Added the image resize feature
- Added the image export feature
- Added the resize window to image size feature
- Added the zoom to fit window size feature
- Added warnings for invalid filepaths
- Added warnings when trying to filter an empty canvas
- Added the pencil / drawing function
- Added the colour selector
- Error handling
- Added previews for filters and brightness/contrast
- Added additional submenus
- Sorted CI 

#### Rochelle
- Added image rotation
- Added image flip features
- Macros
- Toolbar

#### Isaac
- Added internationalisation
- Added the UI and logic for Gaussian Blur
- Added exception handling for Language
- Added the ability for the program to remember the previous language used
- Keyboard shortcuts
- Added the HTML (in-app) help menu

#### Nic
- Added the brightness adjustment feature
- Added the contrast adjustment feature
- Separated corrupt image handling from invalid filepaths - handled in EditableImage.open()
- Error handling for cases where the user doesn't have access privileges to the requested image file
- Helped integrate sliders for user input in other features
- Added mouse selection and cropping
- Added shape drawing functions

#### James
- Added sharpness filter adjustment feature
- Added median filter adjustment feature
- Implemented filter actions applicable to sharpness and median filter adjustment features
- Implement slider for median filter adjustment feature
- Added the sobel filter
- Added the emboss filter

## Features that were added - Part One
The team generally stuck to the checklist for part 1 to ensure that features were implemented to a high standard. However, some other changes were made.

### Zoom to fit window size
An additional option within the view menu that adjusts the zoom of the current image to fill ANDIE's window

### Resize window to image size
Another additional option within the view menu that adjusts ANDIE's window size according to the current image. This is automatically called when a new image is opened within ANDIE.

### Additional languages
Though multilingual support was listed in the project's requirements, two additional languages - Japanese and Māori - were added rather than just one

### Additional warnings
A number of new warning dialogs were added, such as those that appear when an invalid filepath is entered or the user attempts to filter a blank canvas. Additionally, there are new dialogs for corrupt image files and errors occuring due to lack of access privileges. 

## Features that were added - Part Two

### HTML Help Menu
A user-friendly alternative to the automatically generated Javadoc site that users can instead access directly via ANDIE

### Drawing pencil and colour selection
Lets the user draw on images  
Several different pencil widths are available  
Of course, the pencil's colour can also be set with the colour selector

## Current progress  

### Deliverable One - April 6th 2023
At this stage all features required for the first deliverable have been implemented and thoroughly tested. There are a few features we would like to add in the future such as a warning when a user exits ANDIE without saving but these will be done after the mid-semester break. We are all quite happy with the progress, having finished everything several days before the deadline and hope to continue working at the same rate after the break.

### Deliverable Two - As at May 22nd 2023
At this stage most features have been implemented and thoroughly tested