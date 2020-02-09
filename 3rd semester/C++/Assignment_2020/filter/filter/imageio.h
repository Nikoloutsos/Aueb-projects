//------------------------------------------------------------
//
// C++ course assignment code 
//
// G. Papaioannou, 2019 -2020
//
//

#ifndef _IMAGEIO
#define _IMAGEIO

#include <iostream>

namespace image
{
	class ImageIO
	{
	protected:
		ImageIO() {}
	public:
		/*!
		 * Loads the image data from the specified file, if the extension of the filename matches the format string.
		 *
		 * Only the "ppm" extension is supported for now. The extension comparison should be case-insensitive.
		 *
		 * \param filename is the string of the file to read the array data from.
		 * \param format specifies the file format according to which the array data should be decoded from the file.
		 * Only the "ppm" format is a valid format string for now.
		 *
		 * \return true if the loading completes successfully, false otherwise.
		 */
		virtual bool load(const std::string & filename, const std::string & format) = 0;

		/*!
		* Stores the image data to the specified file, if the extension of the filename matches the format string.
		*
		* Only the "ppm" extension is supported for now. The extension comparison should be case-insensitive.
		*
		* \param filename is the string of the file to write the array data to.
		* \param format specifies the file format according to which the array data should be encoded to the file.
		* Only the "ppm" format is a valid format string for now.
		*
		* \return true if the save operation completes successfully, false otherwise.
		*/
		virtual bool save(const std::string & filename, const std::string & format) = 0;
	};

} //namespace image

#endif