#include <stdint.h>

typedef unsigned char byte;
typedef uint32_t prgb;
typedef uint32_t idate;

// ====== data definitions ======

struct point {
  double x;
  double y;
};

// The idate_array struct is a "safe" array.
// It is meant to be used with the safe read and write operations
// specified below.
struct idate_array {
  idate *dates;
  unsigned int len;
};

// An image consists of a width in pixels, a height in pixels, and
// then an array each for the red, green, and blue bytes of each pixel
// in the image, in row-by-row order. Note that necessarily each array
// is (width*height) bytes long.
struct image {
  unsigned int width;
  unsigned int height;
  byte *reds;
  byte *greens;
  byte *blues;
};

// ====== subroutine headers ======

// The function format_date should consume an idate like 20240406 and
// produce a heap-allocated string of the form "April 6, 2024". There
// should be no leading zero before the day (that is, the string
// should not include "April 06").
// GIGO if the date is nonsense.
char *format_date(idate d);

// Produce a new heap-allocated point with given values.
// If allocation fails, print an error message to stderr and exit(1).
struct point *point_new(double x, double y);

// Produce a string of the form "(x,y)" for the x and y coordinates in
// the pointed-to string struct. In the string, both x and y must be
// specified to exactly four digits past the decimal point. Use the
// format code "%.4f" to achieve this. (Use sprintf.)
// GIGO if p is NULL.
char *point_tos(struct point *p);

// Produce a new heap-allocated point whose x coordinate has been
// adjusted by dx.
// GIGO if p is NULL.
struct point *push_x(struct point *p, double dx);

// Produce a new heap-allocated point whose y coordinate has been
// adjusted by dy.
// GIGO if p is NULL.
struct point *push_y(struct point *p, double dy);

// Produce a new heap-allocated point that is exactly halfway to the
// origin relative to the argument point. (In other words, the
// midpoint of p and the origin.)
// GIGO if p is NULL.
struct point *towards_origin(struct point *p);

// Compute the Euclidean distance from p to q.
// GIGO if p and/or q is NULL.
double distance(struct point *p, struct point *q);

// Return "true" if p and q inhabit the same quadrant. Note that a
// point on an axis is in no quadrant. Therefore this function can
// return "true" only if both p and q are not on an axis.
// GIGO if p and/or q is NULL.
int same_quadrant(struct point *p, struct point *q);

// Safely read from the array, checking first whether i is within
// bounds.  If i is out of bounds, print an error message to stderr
// and exit(1).
// GIGO if a is NULL.
idate ida_read(struct idate_array *a, int i);

// Safely write to the array, checking first whether i is within
// bounds.  If i is out of bounds, print an error message to stderr
// and exit(1).
// GIGO if a is NULL.
void ida_write(struct idate_array *a, int i, idate d);

// Build an array with the given start date and the given length.The
// dates in the array must start at the start date and continue day by
// day up to the given length. Note that the empty idate_array is
// legal and reasonable. An empty idate_array would include the NULL
// pointer in the array slot, and 0 in the length slot, but not itself
// be NULL.
// GIGO if the start date is nonsense.
struct idate_array *ida_build(idate start, unsigned int len);

// Free the idate_array from the heap.
// GIGO if a doesn't point to the heap.
void ida_free(struct idate_array *a);
			     
// Build an image of the same color and the given number of pixels.
// If w or h is 0, then all three byte arrays should be NULL, but the
// image pointer itself should not be NULL.
struct image *img_solid(unsigned int w, unsigned int h, prgb c);

// Free the image from the heap.
// GIGO if img doesn't point to the heap.
void img_free(struct image *img);

// Compute the mean luminance of all pixels in the image.  If the
// image is the empty image (containing zero pixels), print an error
// message to stderr and exit(1).
// GIGO if img is NULL.
double mean_luminance(struct image *img);

// Creates a new idate_array struct given a pointer to an array of 
// dates and its length.
struct idate_array *idarray_new(idate *dates, unsigned int len);