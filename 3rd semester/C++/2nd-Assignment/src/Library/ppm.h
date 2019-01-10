//------------------------------------------------------------
//
// C++ course assignment code 
//
// G. Papaioannou, 2017
//
//
//-------------------------------------------------------------

#ifndef _PPM
#define _PPM

namespace imaging
{
	/*! Reads a PPM image file and returns a pointer to a newly allocated float array containing the image data.
	 *
	 * Values in the returned array are in the range [0,1] and for each pixel, three values are stored.
	 *
	 *  \param filename is the null-terminated string of the name of the file to open.
	 *  \param w is the address of an integer parameter to return the width of the image into.
	 *  \param h is the address of an integer parameter to return the height of the image into.
	 *
	 *  \return a pointer to a new float array of 3 X w X h floats, that contains the image data.
	 *  If the reading operation failed the function returns nullptr.
	 */
	float * ReadPPM(const char * filename, int * w, int * h);

	/*! Writes an image buffer as a PPM file.
	*
	*  \param data contains the image buffer. The data are RGB float tripplets with values in the range [0,1].
	*  \param w is the width of the image in pixels.
	*  \param h is the height of the image in pixels.
	*  \param filename is the null-terminated string of the name of the file to create.
	*
	*  \return true if the image save operation was successful, false otherwise. If the data parameter is nulltr, the
	*  function returns immediately with a false return value.
	*/
	bool WritePPM(const float * data, int w, int h, const char * filename);

} //namespace imaging

#endif
