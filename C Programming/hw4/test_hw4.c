#include <stdio.h>
#include <criterion/criterion.h>
#include <math.h>
#include "hw4.h"

int pt_struct_eq(struct point *a, struct point *b, double epsilon)
{
  return fabs(a->x - b->x) < epsilon && fabs(a->y - b->y) < epsilon;
}

int idate_arr_eq(struct idate_array *a, struct idate_array *b)
{
  if (a->len != b->len){
    return 0;
  }
  for(int i=0; i<(a->len); i++){
    if (a->dates[i] != b->dates[i]){
      return 0;
    }
  }
  return 1;
}

int val_in_all_array(byte val, byte *arr, int len)
{
  for(int i=0; i<len; i++){
    if (val != arr[i]){
      return 0;
    }
  }
  return 1;
}

int within(double epsilon, double a, double b)
{
  return fabs(a-b)<=epsilon;
}

// ======== tests

// -------- format_date

Test(hw4_format_date, format_date00)
{
  char *c = format_date(20230923);
  cr_assert(strcmp(c, "September 23, 2023") == 0); 
}

Test(hw4_format_date, format_date01)
{
  char *c = format_date(20141118);
  cr_assert(strcmp(c, "November 18, 2014") == 0);
}

Test(hw4_format_date, format_date02)
{
  char *c = format_date(20160809);
  cr_assert(strcmp(c, "August 9, 2016") == 0);
}

// -------- point_new

Test(hw4_point_new, point_new00)
{
  struct point *pt = point_new(3.0, 2.9);
  cr_assert((*pt).x==3.0 && (*pt).y==2.9);
}

Test(hw4_point_new, point_new01)
{
  struct point *pt = point_new(-1.88, 3.056);
  cr_assert((*pt).x==-1.88 && (*pt).y==3.056);
}

Test(hw4_point_new, point_new02)
{
  struct point *pt = point_new(3, 2);
  cr_assert((*pt).x==3.0 && (*pt).y==2.0);
}

// -------- point_tos

Test(hw4_point_tos, point_tos00)
{
  struct point *pt = point_new(2.03, 3.03);
  char *c = point_tos(pt);
  cr_assert(strcmp(c, "(2.0300,3.0300)") == 0);
}

Test(hw4_point_tos, point_tos01)
{
  struct point *pt = point_new(2333.141592, 453.135678);
  char *c = point_tos(pt);
  cr_assert(strcmp(c, "(2333.1416,453.1357)") == 0);
}

Test(hw4_point_tos, point_tos02)
{
  struct point *pt = point_new(-1.23, 4.20);
  char *c = point_tos(pt);
  cr_assert(strcmp(c, "(-1.2300,4.2000)") == 0);
}

// -------- push_x

Test(hw4_push_x, push_x00)
{
  struct point *pt1 = point_new(3.3, 2.45);
  *pt1 = *push_x(pt1, 0.2);
  struct point *pt2 = point_new(3.5, 2.45);
  cr_assert(pt_struct_eq(pt1, pt2, 0.00001));
}

Test(hw4_push_x, push_x01)
{
  struct point *pt1 = point_new(-2.452, 3.01);
  *pt1 = *push_x(pt1, 0.452);
  struct point *pt2 = point_new(-2.0, 3.01);
  cr_assert(pt_struct_eq(pt1, pt2, 0.00001));
}

Test(hw4_push_x, push_x02)
{
  struct point *pt1 = point_new(-452, -3.01);
  *pt1 = *push_x(pt1, -5);
  struct point *pt2 = point_new(-457, -3.01);
  cr_assert(pt_struct_eq(pt1, pt2, 0.00001));
}

// -------- push_y

Test(hw4_push_y, push_y00)
{
  struct point *pt1 = point_new(3.3, 2.45);
  *pt1 = *push_y(pt1, 0.55);
  struct point *pt2 = point_new(3.3, 3.0);
  cr_assert(pt_struct_eq(pt1, pt2, 0.00001));
}

Test(hw4_push_y, push_y01)
{
  struct point *pt1 = point_new(3.3, -2.01);
  *pt1 = *push_y(pt1, 0.01);
  struct point *pt2 = point_new(3.3, -2);
  cr_assert(pt_struct_eq(pt1, pt2, 0.00001));
}

Test(hw4_push_y, push_y02)
{
  struct point *pt1 = point_new(3.3, -45.345);
  *pt1 = *push_y(pt1, 2.456);
  struct point *pt2 = point_new(3.3, -42.889);
  cr_assert(pt_struct_eq(pt1, pt2, 0.00001));
}

// -------- towards_origin

Test(hw4_towards_origin, towards_origin00)
{
  struct point *pt = point_new(0,0);
  *pt = *towards_origin(pt);
  struct point *m_way = point_new(0,0);
  cr_assert(pt_struct_eq(pt, m_way, 0.00001));
}

Test(hw4_towards_origin, towards_origin01)
{
  struct point *pt = point_new(10,5);
  *pt = *towards_origin(pt);
  struct point *m_way = point_new(5,2.5);
  cr_assert(pt_struct_eq(pt, m_way, 0.00001));
}

Test(hw4_towards_origin, towards_origin02)
{
  struct point *pt = point_new(-7,8);
  *pt = *towards_origin(pt);
  struct point *m_way = point_new(-3.5,4);
  cr_assert(pt_struct_eq(pt, m_way, 0.00001));
}

// -------- distance

Test(hw4_distance, distance00)
{
  struct point *pt1 = point_new(3,3);
  struct point *pt2 = point_new(3,3);
  cr_assert(within(0.001, distance(pt1, pt2), 0));
}

Test(hw4_distance, distance01)
{
  struct point *pt1 = point_new(1,1);
  struct point *pt2 = point_new(-2,-3);
  cr_assert(within(0.001, distance(pt1, pt2), 5));
}

Test(hw4_distance, distance02)
{
  struct point *pt1 = point_new(-1.5, 2.5);
  struct point *pt2 = point_new(3.5, 5.5);
  cr_assert(within(0.001, distance(pt1, pt2), 5.831));
}

Test(hw4_distance, distance03)
{
  struct point *pt1 = point_new(4.57, 10.232);
  struct point *pt2 = point_new(2.31, 4.1234);
  cr_assert(within(0.01, distance(pt1, pt2), 6.5103));
}

// -------- same_quadrant

Test(hw4_same_quadrant, same_quadrant00)
{
  struct point *pt1 = point_new(4.57, 10.232);
  struct point *pt2 = point_new(2.31, 4.1234);
  cr_assert(same_quadrant(pt1, pt2));
}

Test(hw4_same_quadrant, same_quadrant01)
{
  struct point *pt1 = point_new(3,0);
  struct point *pt2 = point_new(4,5);
  cr_assert(!same_quadrant(pt1, pt2));
}

Test(hw4_same_quadrant, same_quadrant02)
{
  struct point *pt1 = point_new(-1,5);
  struct point *pt2 = point_new(-2,-3.5);
  cr_assert(!same_quadrant(pt1, pt2));
}

Test(hw4_same_quadrant, same_quadrant03)
{
  struct point *pt1 = point_new(-4.53, -4.23);
  struct point *pt2 = point_new(-2.3, -0.5);
  cr_assert(same_quadrant(pt1, pt2));
}

// -------- ida_read

Test(hw4_ida_read, ida_read00)
{
  idate arr[3] = {20230302, 20241231, 20251006};
  struct idate_array *d = idarray_new(arr, 3);
  cr_assert(ida_read(d, 2) == 20251006);
  ida_free(d);
}

Test(hw4_ida_read, ida_read01, .exit_code = 1) {
  idate arr[3] = {20230302, 20241231, 20251006};
  struct idate_array *d = idarray_new(arr, 3);
  ida_read(d, 3);
  ida_free(d);
}

Test(hw4_ida_read, ida_read02)
{
  idate arr[4] = {20230302, 20241231, 20251006, 20210304};
  struct idate_array *d = idarray_new(arr, 4);
  cr_assert(ida_read(d, 3) == 20210304);
  ida_free(d);
}

// -------- ida_write

Test(hw4_ida_write, ida_write00)
{
  idate arr1[4] = {20230302, 20241231, 20251006, 20210304};
  struct idate_array *a = idarray_new(arr1, 4);
  ida_write(a, 0, 20260102);
  idate arr2[4] = {20260102, 20241231, 20251006, 20210304};
  struct idate_array *b = idarray_new(arr2, 4);
  cr_assert(idate_arr_eq(a, b));
  ida_free(a);
  ida_free(b);
}

Test(hw4_ida_write, ida_write01)
{
  idate arr1[5] = {20230302, 20241231, 20251006, 20210304, 20240506};
  struct idate_array *a = idarray_new(arr1, 5);
  ida_write(a, 4, 20260102);
  ida_write(a, 1, 20210430);
  idate arr2[5] = {20230302, 20210430, 20251006, 20210304, 20260102};
  struct idate_array *b = idarray_new(arr2, 5);
  cr_assert(idate_arr_eq(a, b));
  ida_free(a);
  ida_free(b);
}

Test(hw4_ida_write, ida_write02, .exit_code = 1)
{
  idate arr1[5] = {20230302, 20241231, 20251006, 20210304, 20240506};
  struct idate_array *a = idarray_new(arr1, 5);
  ida_write(a, 5, 20260102);
  ida_free(a);
}

// -------- ida_build

Test(hw4_ida_build, ida_build00)
{
  struct idate_array *a = ida_build(20240302, 3);
  idate arr[3] = {20240302, 20240303, 20240304};
  struct idate_array *b = idarray_new(arr,3);
  cr_assert(idate_arr_eq(a,b));
}

Test(hw4_ida_build, ida_build01)
{
  struct idate_array *a = ida_build(20240228, 4);
  idate arr[4] = {20240228, 20240229, 20240301, 20240302};
  struct idate_array *b = idarray_new(arr,4);
  cr_assert(idate_arr_eq(a,b));
  ida_free(a);
  ida_free(b);
}

Test(hw4_ida_build, ida_build02)
{
  struct idate_array *a = ida_build(20231230, 5);
  idate arr[5] = {20231230, 20231231, 20240101, 20240102, 20240103};
  struct idate_array *b = idarray_new(arr,5);
  cr_assert(idate_arr_eq(a,b));
  ida_free(a);
  ida_free(b);
}

Test(hw4_ida_build, ida_build03)
{
  struct idate_array *a = ida_build(20231230, 0);
  struct idate_array *b = idarray_new(NULL,0);
  cr_assert(idate_arr_eq(a,b));
  ida_free(a);
  ida_free(b);
}

// -------- img_solid

Test(hw4_img_solid, img_solid00)
{
  struct image *new_img = img_solid(10,12,0xFFFFFF00);
  cr_assert(val_in_all_array(0xFF, new_img->reds, (new_img->width)*(new_img->height)));
  cr_assert(val_in_all_array(0xFF, new_img->greens, (new_img->width)*(new_img->height)));
  cr_assert(val_in_all_array(0xFF, new_img->blues, (new_img->width)*(new_img->height)));
  cr_assert(new_img->width==10 && new_img->height==12);
  img_free(new_img);
}

Test(hw4_img_solid, img_solid01)
{
  struct image *new_img = img_solid(4, 4, 0xDD00CC00);
  cr_assert(val_in_all_array(0xDD, new_img->reds, (new_img->width) * (new_img->height)));
  cr_assert(val_in_all_array(0x00, new_img->greens, (new_img->width) * (new_img->height)));
  cr_assert(val_in_all_array(0xCC, new_img->blues, (new_img->width) * (new_img->height)));
  cr_assert(new_img->width==4 && new_img->height==4);
  img_free(new_img);
}

Test(hw4_img_solid, img_solid02)
{
  struct image *new_img = img_solid(4, 0, 0xDD00CC00);
  cr_assert(new_img->reds == NULL);
  cr_assert(new_img->greens == NULL);
  cr_assert(new_img->blues == NULL);
  cr_assert(new_img->width==4 && new_img->height==0);
  img_free(new_img);
}

// -------- mean_luminance

Test(hw4_mean_luminance, mean_luminance00)
{
  struct image *new_img = img_solid(10,12,0xFFFFFF00);
  cr_assert(within(0.001, mean_luminance(new_img), 255));
  img_free(new_img);
}

Test(hw4_mean_luminance, mean_luminance01)
{
  struct image *new_img = img_solid(6,10,0x01010100);
  cr_assert(within(0.001,mean_luminance(new_img),1));
  img_free(new_img);
}

Test(hw4_mean_luminance, mean_luminance02)
{
  struct image *new_img = img_solid(6,6,0);
  cr_assert(within(0.001,mean_luminance(new_img),0));
  img_free(new_img);
}

Test(hw4_mean_luminance, mean_luminance03)
{
  struct image *new_img = img_solid(5,10,0x00FF0000);
  cr_assert(within(0.001,mean_luminance(new_img),182.376));
  img_free(new_img);
}

Test(hw4_mean_luminance, mean_luminance04)
{
  struct image *new_img = img_solid(5,10,0xDD000000);
  cr_assert(within(0.001,mean_luminance(new_img),46.985));
  img_free(new_img);
}