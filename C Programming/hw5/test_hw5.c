#include <stdio.h>
#include <criterion/criterion.h>
#include <math.h>
#include "hw5.h"

// ======== tests

int within(double epsilon, double a, double b)
{
  return fabs(a-b)<=epsilon;
}

// -------- concat

Test(hw5_concat, concat00)
{
  char *a = "abc";
  char *b = "defg";
  char *ab = concat(a,b);
  cr_assert(strcmp(ab, "abcdefg") == 0);
  free(ab);
}

Test(hw5_concat, concat01)
{
  char *a = "";
  char *b = "a";
  char *ab = concat(a,b);
  cr_assert(strcmp(ab, "a") == 0);
  free(ab);
}

Test(hw5_concat, concat02)
{
  char *a = "-13";
  char *b = "4+5";
  char *ab = concat(a,b);
  cr_assert(strcmp(ab, "-134+5") == 0);
  free(ab);
}

// -------- integral

double f1(double x)
{
  double y = (pow(x, 2) / 30.0) + 30;
  return y;
}

double f2(double x)
{
  double y = sin(x) + 20;
  return y;
}

Test(hw5_integral, integral00)
{
  cr_assert(within(0.001,integral(f1,0.0,1.0,0.19),28.506859));
}

Test(hw5_integral, integral01)
{
  cr_assert(within(0.001,integral(f1,0.0,11.0,0.01),344.768728));
}

Test(hw5_integral, integral02)
{
  cr_assert(within(0.001,integral(f2,0.0,1.0,0.1),20.417240));
}

// -------- caps

char_list *build_cl(char *s)
{
  char_list *new_cl = NULL;
  for(int i=0; i<strlen(s); i++){
    new_cl = cl_cons(s[i], new_cl);
  }
  return new_cl;
}

int cl_eq(char_list *a, char_list *b)
{
  char_list *current_a = a;
  char_list *current_b = b;
  while(current_a != NULL && current_b != NULL){
    if(current_a->first != current_b->first){
      return 0;
    }
    current_a = current_a->rest;
    current_b = current_b->rest;
  }
  if(current_a == NULL && current_b == NULL){
    return 1;
  }
  return 0;
}

Test(hw5_caps, caps00)
{
  char *a = "abcDEfGIJfG";
  char *b = "DEGIJG";
  char_list *c = caps(a);
  char_list *d = build_cl(b);
  cr_assert(cl_eq(c, d));
  cl_free(c);
  cl_free(d);
}

Test(hw5_caps, caps01)
{
  char *a = "GrAnNY";
  char *b = "GANY";
  char_list *c = caps(a);
  char_list *d = build_cl(b);
  cr_assert(cl_eq(c, d));
  cl_free(c);
  cl_free(d);
}

Test(hw5_caps, caps02)
{
  char *a = "abcdef";
  char *b = "";
  char_list *c = caps(a);
  char_list *d = build_cl(b);
  cr_assert(cl_eq(c, d));
  cl_free(c);
  cl_free(d);
}

// -------- bitstring

Test(hw5_bitstring, bistring00)
{
  unsigned char a = 255;
  char *b = bitstring(a);
  cr_assert(strcmp(b, "11111111") == 0);
  free(b);
}

Test(hw5_bitstring, bistring01)
{
  unsigned char a = 1;
  char *b = bitstring(a);
  cr_assert(strcmp(b, "00000001") == 0);
  free(b);
}

Test(hw5_bitstring, bistring02)
{
  unsigned char a = 171;
  char *b = bitstring(a);
  cr_assert(strcmp(b, "10101011") == 0);
  free(b);
}

// -------- polygon_perim

point *build_pt(double x, double y)
{
  point *new_pt = malloc(sizeof(point));
  if(new_pt==NULL){
    fprintf(stderr, "Unable to allocate memory. \n");
    exit(1);
  }
  new_pt->x = x;
  new_pt->y = y;
  return new_pt;
}

point_list *build_pl(point **pts, int len)
{
  point_list *new_pl = NULL;
  for(int i=0; i<len; i++){
    new_pl = pl_cons(*pts[i], new_pl);
  }
  return new_pl;
}

Test(hw5_polygon_perim, polygon_perim00)
{
  point *pt1 = build_pt(0,0);
  point *pt2 = build_pt(3,0);
  point *pt3 = build_pt(3,3);
  point *pt4 = build_pt(0,3);
  point *pts[4] = {pt1, pt2, pt3, pt4};
  point_list *all_pts = build_pl(pts, 4);
  cr_assert(within(0.001,polygon_perim(all_pts),12));
}

Test(hw5_polygon_perim, polygon_perim01, .exit_code=1)
{
  point *pt1 = build_pt(0,0);
  point *pt2 = build_pt(3,0);
  point *pts[2] = {pt1, pt2};
  point_list *all_pts = build_pl(pts, 2);
  polygon_perim(all_pts);
}

Test(hw5_polygon_perim, polygon_perim02)
{
  point *pt1 = build_pt(0,0);
  point *pt2 = build_pt(0,2);
  point *pt3 = build_pt(2,2);
  point *pts[3] = {pt1, pt2, pt3};
  point_list *all_pts = build_pl(pts, 3);
  cr_assert(within(0.001,polygon_perim(all_pts),6.828427));
}

Test(hw5_polygon_perim, polygon_perim03)
{
  point *pt1 = build_pt(2,0);
  point *pt2 = build_pt(5,5);
  point *pt3 = build_pt(-2,8);
  point *pts[3] = {pt1, pt2, pt3};
  point_list *all_pts = build_pl(pts, 3);
  cr_assert(within(0.001,polygon_perim(all_pts),22.390997));
}

Test(hw5_polygon_perim, polygon_perim04)
{
  point *pt1 = build_pt(-6,-4);
  point *pt2 = build_pt(-2,-1);
  point *pt3 = build_pt(-2,8);
  point *pt4 = build_pt(1.5,2.3);
  point *pts[4] = {pt1, pt2, pt3, pt4};
  point_list *all_pts = build_pl(pts, 4);
  cr_assert(within(0.001,polygon_perim(all_pts),30.48369));
}

// -------- linreg

lineq *build_lq(double m, double b)
{
  lineq *new_lq = malloc(sizeof(lineq));
  if(new_lq==NULL){
    fprintf(stderr, "Unable to allocate memory. \n");
    exit(1);
  }
  new_lq->m = m;
  new_lq->b = b;
  return new_lq;
}

int lq_eq(lineq *i, lineq *j)
{
  return within(0.001,i->m,j->m) && within(0.001,i->b,j->b);
}

Test(hw5_linreg, linreg00)
{
  point *pt1 = build_pt(0,0);
  point *pt2 = build_pt(1,1);
  point *pt3 = build_pt(2,2);
  point *pts[3] = {pt1, pt2, pt3};
  point_list *all_pts = build_pl(pts, 3);
  lineq *lreg1 = build_lq(1,0);
  lineq lreg2 = linreg(all_pts);
  cr_assert(lq_eq(lreg1, &lreg2));
  pl_free(all_pts);
  free(pt1);
  free(pt2);
  free(pt3);
  free(lreg1);
}

Test(hw5_linreg, linreg01)
{
  point *pt1 = build_pt(1,0);
  point *pt2 = build_pt(1,1);
  point *pt3 = build_pt(3,3);
  point *pt4 = build_pt(-10,-5);
  point *pts[4] = {pt1, pt2, pt3, pt4};
  point_list *all_pts = build_pl(pts, 4);
  lineq *lreg1 = build_lq(0.560859,0.451074);
  lineq lreg2 = linreg(all_pts);
  cr_assert(lq_eq(lreg1, &lreg2));
  pl_free(all_pts);
  free(pt1);
  free(pt2);
  free(pt3);
  free(lreg1);
}

Test(hw5_linreg, linreg02, .exit_code=1)
{
  point *pt1 = build_pt(1,0);
  point *pts[1] = {pt1};
  point_list *all_pts = build_pl(pts, 1);
  linreg(all_pts);
  pl_free(all_pts);
  free(pt1);
}

Test(hw5_linreg, linreg03)
{
  point *pt1 = build_pt(1.1,0);
  point *pt2 = build_pt(-5.4,10.2);
  point *pt3 = build_pt(10.3,-0.3);
  point *pt4 = build_pt(-1.3,-18.3);
  point *pts[4] = {pt1, pt2, pt3, pt4};
  point_list *all_pts = build_pl(pts, 4);
  lineq *lreg1 = build_lq(-0.184803,-1.88286);
  lineq lreg2 = linreg(all_pts);
  cr_assert(lq_eq(lreg1, &lreg2));
  pl_free(all_pts);
  free(pt1);
  free(pt2);
  free(pt3);
  free(lreg1);
}

// -------- total_length

str_list *build_sl(char **strs, int len)
{
  str_list *new_sl = NULL;
  for(int i=0; i<len; i++){
    new_sl = sl_cons(strs[i], new_sl);
  }
  return new_sl;
}

Test(hw5_total_length, total_length00)
{
  char *strs[4] = {"abc", "def", "gh", "ij"};
  str_list *strings = build_sl(strs, 4);
  cr_assert(total_length(strings) == 10);
  sl_free(strings);
}

Test(hw5_total_length, total_length01)
{
  char *strs[6] = {"12345", "6", "", "78", "9", "10"};
  str_list *strings = build_sl(strs, 6);
  cr_assert(total_length(strings) == 11);
  sl_free(strings);
}

Test(hw5_total_length, total_length02)
{
  char *strs[1] = {""};
  str_list *strings = build_sl(strs, 1);
  cr_assert(total_length(strings) == 0);
  sl_free(strings);
}

// -------- split

int sl_eq(str_list *a, str_list *b)
{
  str_list *current_a = a;
  str_list *current_b = b;
  while(current_a != NULL && current_b != NULL){
    if(strcmp(current_a->first, current_b->first) != 0){
      return 0;
    }
    current_a = current_a->rest;
    current_b = current_b->rest;
  }
  if(current_a == NULL && current_b == NULL){
    return 1;
  }
  return 0;
}

Test(hw5_split, split00)
{
  char *str = "abc/def/ghij";
  char *strs[3] = {"abc", "def", "ghij"};
  str_list *a = split('/',str);
  str_list *b = build_sl(strs, 3);
  cr_assert(sl_eq(a,b));
  sl_free(a);
  sl_free(b);
}

Test(hw5_split, split01)
{
  char *str = "/a";
  char *strs[2] = {"", "a"};
  str_list *a = split('/',str);
  str_list *b = build_sl(strs, 2);
  cr_assert(sl_eq(a,b));
  sl_free(a);
  sl_free(b);
}

Test(hw5_split, split02)
{
  char *str = "--a--";
  char *strs[5] = {"", "", "a", "", ""};
  str_list *a = split('-',str);
  str_list *b = build_sl(strs, 5);
  cr_assert(sl_eq(a,b));
  sl_free(a);
  sl_free(b);
}

Test(hw5_split, split03)
{
  char *str = "ab//c/d";
  char *strs[4] = {"ab", "", "c", "d"};
  str_list *a = split('/',str);
  str_list *b = build_sl(strs, 4);
  cr_assert(sl_eq(a,b));
  sl_free(a);
  sl_free(b);
}

Test(hw5_split, split04)
{
  char *str = "abcde//";
  char *strs[3] = {"abcde", "", ""};
  str_list *a = split('/',str);
  str_list *b = build_sl(strs, 3);
  cr_assert(sl_eq(a,b));
  sl_free(a);
  sl_free(b);
}

// -------- join

Test(hw5_join, join00)
{
  char *str = "do*the*birds*sing*?";
  char *strs[5] = {"do", "the", "birds", "sing", "?"};
  str_list *strs_sl = build_sl(strs, 5);
  char *j = join('*',strs_sl);
  cr_assert(strcmp(str, j) == 0);
  sl_free(strs_sl);
}

Test(hw5_join, join01)
{
  char *str = "a2b2cc2dd";
  char *strs[4] = {"a", "b", "cc", "dd"};
  str_list *strs_sl = build_sl(strs, 4);
  char *j = join('2',strs_sl);
  cr_assert(strcmp(str, j) == 0);
  sl_free(strs_sl);
}

Test(hw5_join, join02)
{
  char *str = "*";
  char *strs[2] = {"", ""};
  str_list *strs_sl = build_sl(strs, 2);
  char *j = join('*',strs_sl);
  cr_assert(strcmp(str, j) == 0);
  sl_free(strs_sl);
}

Test(hw5_join, join03)
{
  char *str = "";
  char *strs[1] = {""};
  str_list *strs_sl = build_sl(strs, 1);
  char *j = join('*',strs_sl);
  cr_assert(strcmp(str, j) == 0);
  sl_free(strs_sl);
}

// EXTRA

int int_arr_eq(int *a, int *b, int len)
{
  for(int i=0; i<len; i++){
    if(a[i] != b[i]){
      return 0;
    }
  }
  return 1;
}

Test(hw5_square, square00)
{
  int nums[10] = {1,2,3,4,5,6,7,8,9,10};
  int *squared_nums = square_nums(nums, 10, 2);
  int sq_nums[10] = {1,4,9,16,25,36,49,64,81,100};
  cr_assert(int_arr_eq(squared_nums, sq_nums, 10));
  free(squared_nums);
}