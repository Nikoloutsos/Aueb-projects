//
// C++ course assignment code
//
// G. Papaioannou, 2017 - 2018
//
//

#ifndef _IMAGE
#define _IMAGE
#include "Array.h"

using namespace math;




#include <string>
#include "ppm.h"

/*! The imaging namespace contains every class or function associated with the image storage, compression and manipulation.
 */
namespace imaging
{

	//------------------------------------ class Image ------------------------------------------------

		/*! It is the class that represents a generic data container for an image.
		 *
		 * It holds the actual buffer of the pixel values and provides methods for accessing them,
		 * either as individual pixels or as a memory block. The Image class alone does not provide
		 * any functionality for loading and storing an image, as it is the result or input to such a procedure.
		 *
		 * The internal buffer of an image object stores the actual bytes (data) of the color image as
		 * a contiguous sequence of Color structures. Hence, the size of the buffer variable holding these data is
		 * width X height X sizeof(Color) bytes.
		 *
		 * All values stored in the Color data type components are assumed to be floating point values and for typical (normalized)
		 * intensity ranges, each color component is within the range [0.0, 1.0].
		 */
	class Image: public Array<Vec3<float>>
	{
	public:
		// metric accessors

		/*! Returns the width of the image
		 */
		const unsigned int getWidth() const { return width;}



		/*! Returns the height of the image
		 */
		const unsigned int getHeight() const { return height; }
		// data accessors

		/*! Obtains a pointer to the internal data.
		 *
		 *  This is NOT a copy of the internal image data, but rather a pointer
		 *  to the internally allocated space, so DO NOT attempt to delete the pointer.
		 */
		// Vec3<float> * getRawDataPtr();
		/*! Obtains the color of the image at location (x,y).
		 *
		 *  The method should do any necessary bounds checking.
		 *
		 *  \param x is the (zero-based) horizontal index of the pixel to get.
		 *  \param y is the (zero-based) vertical index of the pixel to get.
		 *
		 *  \return The color of the (x,y) pixel as a Color object. Returns a black (0,0,0) color in case of an out-of-bounds x,y pair.
		 */
		Vec3<float> getPixel(unsigned int x, unsigned int y) const;
		// data mutators

		/*! Sets the RGB values for an (x,y) pixel.
		 *
		 *  The method should perform any necessary bounds checking.
		 *
		 *  \param x is the (zero-based) horizontal index of the pixel to set.
		 *  \param y is the (zero-based) vertical index of the pixel to set.
		 *  \param value is the new color for the (x,y) pixel.
		 */
		void setPixel(unsigned int x, unsigned int y, Vec3<float> & value);

		/*! Copies the image data from an external raw buffer to the internal image buffer.
		 *
		 *  The member function ASSUMES that the input buffer is of a size compatible with the internal storage of the
		 *  Image object and that the data buffer has been already allocated. If the image buffer is not allocated or the
		 *  width or height of the image are 0, the method should exit immediately.
		 *
		 *  \param data_ptr is the reference to the preallocated buffer from where to copy the data to the Image object.
		 */
		// void setData(const Vec3<float> * & data_ptr);

		// constructors and destructor

		/*! Default constructor.
		 *
		 * By default, the dimensions of the image should be zero and the buffer must be set to nullptr.
		 */
		Image();

		/*! Constructor with width and height specification.
		 *
		 * \param width is the desired width of the new image.
		 * \param height is the desired height of the new image.
		 */
		Image(unsigned int width, unsigned int height);

		/*! Constructor with data initialization.
		 *
		 * \param width is the desired width of the new image.
		 * \param height is the desired height of the new image.
		 * \param data_ptr is the source of the data to copy to the internal image buffer.
		 *
		 * \see setData
		 */
		Image(unsigned int width, unsigned int height, const Vec3<float> * data_ptr);

		/*! Copy constructor.
		 *
		 * \param src is the source image to replicate in this object.
		 */
		Image(const Image &src);

		/*! The Image destructor.
		 */
		~Image();

		/*! Copy assignment operator.
		 *
		 * \param right is the source image.
		 */
		Image & operator = (const Image & right);
		/*!
		 * Loads the image data from the specified file, if the extension of the filename matches the format string.
		 *
		 * Only the "ppm" extension is supported for now. The extension comparison should be case-insensitive. If the
		 * Image object is initialized, its contents are wiped out before initializing it to the width, height and data
		 * read from the file.
		 *
		 * \param filename is the string of the file to read the image data from.
		 * \param format specifies the file format according to which the image data should be decoded from the file.
		 * Only the "ppm" format is a valid format string for now.
		 *
		 * \return true if the loading completes successfully, false otherwise.
		 */
		bool load(const std::string & filename, const std::string & format);

		/*!
		* Stores the image data to the specified file, if the extension of the filename matches the format string.
		*
		* Only the "ppm" extension is supported for now. The extension comparison should be case-insensitive. If the
		* Image object is not initialized, the method should return immediately with a false return value.
		*
		* \param filename is the string of the file to write the image data to.
		* \param format specifies the file format according to which the image data should be encoded to the file.
		* Only the "ppm" format is a valid format string for now.
		*
		* \return true if the save operation completes successfully, false otherwise.
		*/
		bool save(const std::string & filename, const std::string & format);

	};

} //namespace imaging

#endif
