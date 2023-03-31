# ANDIE - A Non-Destructive Image Editor

ANDIE is a fast, lightweight and reliable editor for making basic image modifications

> ANDIE is still in development, so new features are still being added and bugs are still being resolved!

It is also non-destructive, in the sense that it can save an image, a set of image operations and edited version of the image separately

Examples of ANDIE can be seen below
![A screenshot of an image in ANDIE](/assets/andieexample.jpg "An unedited image open in ANDIE")
![A screenshot of an image that has been greyscaled in ANDIE](/assets/andieexamplebw.jpg "An image that has been filtered in ANDIE")
(Example image retrieved from https://lovepik.com/image-500447536/simple.html)

## User guide

### Overview
ANDIE is designed to allow for fast, straightforward usage with helpful warnings to guide users away from potentially damaging actions.
The interface consists of five submenus and an image representing the state of the edited image.
To interact with one of these submenus, simply click on the relevant header at the top of the screen. You will then be
presented with various options, which are outlined in the *submenus* guide below.

### Submenus

#### File
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

#### Edit
> Provides several fundamental editing actions
##### Undo
Undoes the most recent ImageOperation applied
##### Redo
Redoes the most recent ImageOperation that was undone
##### Resize
Resizes an image according to a scale retrieved from the user
The scale must be between 0 and a displayed limit based on the current size of the image

#### View
> Lets the user customise their ANDIE experience 
##### Zoom In
Increases zoom by 10%
##### Zoom Out
Decreases zoom by 10%
##### Zoom to Fit Window Size
Zooms in/out of the image so that it fills ANDIE's current window
##### Zoom Full
Resets the zoom to the image's actual size
##### Language
Lets the user change ANDIE's current language
Currently, the available languages are English, Japanese and Māori
##### Resize Window to Image Size
Resizes ANDIE's window to match the current image size

#### Filter
> Provides a variety of filters for images
##### Sharpen 
Applies a sharpening filter to the image
##### Median Filter
Applies a median filter to the image
##### Mean Filter
Applies a mean filter to the image
##### Gaussian Filter
Applies a Gaussian blur filter to the image
##### Soft Blur
Applies a soft blur filter to the image

#### Colour
> Provides options to adjust the colouring of images
##### Greyscale
Converts the image to black and white (greyscale)
##### Brightness and Contrast
Provides options to adjust image brightness and contrast

## How code was tested

## Who did what

## Features that were added