// ======== data definitions ========

struct point {
  double x;
  double y;
};
typedef struct point point;

struct int_list_node {
  int first;
  struct int_list_node *rest;
};
typedef struct int_list_node int_list;

struct char_list_node {
  char first;
  struct char_list_node *rest;
};
typedef struct char_list_node char_list;

struct string_list_node {
  char *first;
  struct string_list_node *rest;
};
typedef struct string_list_node str_list;

struct point_list_node {
  struct point first;
  struct point_list_node *rest;
};
typedef struct point_list_node point_list; 

struct linear_equation {
  double m;
  double b;
};
typedef struct linear_equation lineq;

struct rgb {
  unsigned char red;
  unsigned char green;
  unsigned char blue;
};

struct image {
  unsigned int width;
  unsigned int height;
  struct rgb *pixels;
};

// ======== utilities ========

int_list *il_cons(int f, int_list *r);
void il_free(int_list *ns);
void il_show(int_list *ns);

char_list *cl_cons(char f, char_list *r);
void cl_free(char_list *cs);
void cl_show(char_list *cs);

str_list *sl_cons(char *f, str_list *r);
void sl_free(str_list *ss);
void sl_show(str_list *ss);

point_list *pl_cons(point f, point_list *r);
void pl_free(point_list *ps);
void pl_show(point_list *ps);

// ======== operations ========

// Please note: ERROR means the function should write an informative
// message to stderr and exit the program.

// Any allocation should be followed by a test of its success or
// failure; a failed allocation should trigger a message to stderr and
// exiting the program.

// concat: build a new heap-allocated string that is the concatenation
// of two argument strings
// ex: concat("abc","yz") --> "abcyz"
// ERROR if either argument is NULL
char *concat(char *s1, char *s2);

// integral: compute the left integral of given function
// This is a generalization of earlier homework problems.
// GIGO if b<a or dx is nonpositive.
double integral(double(*f)(double), double a, double b, double dx);

// caps: build a list of only the capital letters in s
// ex: "AbCdE" --> 'A'->'C'->'E'
// ERROR if s is NULL
char_list *caps(char *s);

// bistring: build an eight-character heap-allocated string like
// "10110110"
char *bitstring(unsigned char b);

// polygon_perim: assume the points in the list are the points
// describing a polygon.
// The polygon implicitly wraps around from the last point to the
// first as its closing segment.
// ERROR if fewer than 2 points in list.
// GIGO if the points don't actually describe a polygon.
double polygon_perim(point_list *polygon);

// linreg: linear regression: compute the slope and intercept of the
// best-fit line. See PDF for formulas,
// ERROR if fewer than 2 points in list.
// GIGO if the points don't actually describe a dataset (by being all
// the same point, for example).
lineq linreg(point_list *dataset);

// total_length: compute the total length of all strings in the list
unsigned int total_length(str_list *strings);

// split: split the string into a list of strings, given the
// separator.
// In the following examples, assume the separator is '/'.
// ex: "a/bb/c" --> "a"->"bb"->"c"
// ex: "a/b/"   --> "a"->"b"->""
// ex: "//a"    --> "" -> "" -> "a"
str_list *split(char sep, char *s);

// join: join the strings with the character j.
// For example, joining the list "X"->"YZ"->"?"
// with the character '*' produces "X*YZ*?"
char *join(char j, str_list *strings);

int *square_nums(int *nums, int size, int n_threads);
void grayscale_par(struct image *img, unsigned int n_threads);