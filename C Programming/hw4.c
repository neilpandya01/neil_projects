#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <math.h>
#include "hw4.h"

char *format_date(idate d)
{
  char *mos[12] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
  int yr = d / 10000;
  char str_mo[10];
  strcpy(str_mo, mos[((d / 100) % 100) - 1]);
  int day = d % 100;
  char *formatted = malloc(18);
  sprintf(formatted, "%s %d, %d", str_mo, day, yr);
  return formatted;
}

struct point *point_new(double x, double y)
{
  struct point *pt = malloc(sizeof(struct point));
  if (pt == NULL) {
    fprintf(stderr, "Unable to allocate memory \n");
    exit(1);
  }
  pt->x = x;
  pt->y = y;
  return pt;
}

int length_int(int n)
{
  int count = 0;
  while(n >= 1){
    n /= 10;
    count++;
  }
  return count;
}

char *point_tos(struct point *p)
{
  int len_x = length_int(p->x);
  int len_y = length_int(p->y);
  char *total_string = malloc(len_x + len_y + 4 + 4 + 3);
  sprintf(total_string, "(%.4f,%.4f)", p->x, p->y);
  return total_string;
}

struct point *push_x(struct point *p, double dx)
{
  struct point *new_pt = point_new((p->x)+dx, p->y);
  return new_pt;
}

struct point *push_y(struct point *p, double dy)
{
  struct point *new_pt = point_new(p->x, (p->y)+dy);
  return new_pt;
}

struct point *towards_origin(struct point *p)
{
  double new_x = (p->x) / 2.0;
  double new_y = (p->y) / 2.0;
  struct point *new_pt = point_new(new_x, new_y);
  return new_pt;
}

double distance(struct point *p, struct point *q)
{
  double x_dist = fabs(p->x - q->x);
  double y_dist = fabs(p->y - q->y);
  double dist = sqrt(pow(x_dist,2) + pow(y_dist,2));
  return dist;
}

int has_same_sign(double a, double b)
{
  if ((a<0 && b<0) || (a>0 && b>0)){
    return 1;
  }
  return 0;
}

int same_quadrant(struct point *p, struct point *q)
{
  if (has_same_sign(p->x, q->x) && has_same_sign(p->y, q->y)){
    return 1;
  }
  return 0;
}

idate ida_read(struct idate_array *a, int i)
{
  if (i<0 || i>=(a->len)){
    fprintf(stderr, "Index out of bounds. \n");
    exit(1);
  }
  return a->dates[i];
}

void ida_write(struct idate_array *a, int i, idate d)
{
  if (i<0 || i>=(a->len)){
    fprintf(stderr, "Index out of bounds. \n");
    exit(1);
  }
  (a->dates)[i] = d;
}

struct idate_array *idarray_new(idate *dates, unsigned int len)
{
  struct idate_array *d = malloc(sizeof(struct idate_array));
  if (d == NULL) {
    fprintf(stderr, "Unable to allocate memory \n");
    exit(1);
  }
  d->len = len;
  if (len==0){
    d->dates = NULL;
  }
  else{
    d->dates = malloc(len * sizeof(idate));
    if (d->dates == NULL) {
      fprintf(stderr, "Unable to allocate memory \n");
      exit(1);
    }
    for(int i=0; i<len; i++){
      (d->dates)[i] = dates[i];
    }
  }
  return d;
}

int days_in_mo(int mo, int yr){
  if (mo==2){
    if (yr%4==0 && (yr%100!=0 || yr%400==0)){
      return 29;
    }
    else{
      return 28;
    }
  }
  if ((mo%2==1 && mo<=7) || (mo%2==0 && mo>=8)){
    return 31;
  }
  return 30;
}

idate *all_dates(idate start, int num_days)
{
  idate *all = malloc(num_days * sizeof(idate));
  int yr = start / 10000;
  int mo = (start / 100) % 100;
  int day = start % 100;
  for (int i=0; i<num_days; i++) {
    all[i] = yr*10000 + mo*100 + day;
    day++;
    if (day > days_in_mo(mo, yr)) {
      day = 1;
      mo++;
      if (mo==13) {
        mo = 1;
        yr++;
      }
    }
  }
  return all;
}

struct idate_array *ida_build(idate start, unsigned int len)
{
  idate *total_dates = all_dates(start, len);
  struct idate_array *built_arr = idarray_new(total_dates, len);
  return built_arr;
}
			     
void ida_free(struct idate_array *a)
{
  if (a != NULL){
    free(a->dates);
    free(a);
  }
}

struct image *img_solid(unsigned int w, unsigned int h, prgb c)
{
  struct image *new_img = malloc(sizeof(struct image));
  new_img->width = w;
  new_img->height = h;
  if (w==0 || h==0){
    new_img->reds = NULL;
    new_img->greens = NULL;
    new_img->blues = NULL;
  }
  else{
    new_img->reds = malloc(w*h*sizeof(byte));
    new_img->greens = malloc(w*h*sizeof(byte));
    new_img->blues = malloc(w*h*sizeof(byte));
    byte r = (c>>24) & 0xFF;
    byte g = (c>>16) & 0xFF;
    byte b = (c>>8) & 0xFF;
    for(int i=0;i<(w*h);i++){
      new_img->reds[i] = r;
      new_img->greens[i] = g;
      new_img->blues[i] = b;
    }
  }
  return new_img;
}

void img_free(struct image *img)
{
  if (img != NULL){
    free(img->reds);
    free(img->greens);
    free(img->blues);
    free(img);
  }
}

double luminance(byte r, byte g, byte b)
{
  return 0.2126 * r + 0.7152 * g + 0.0722 * b;
}

double mean_luminance(struct image *img)
{
  double count = 0;
  for(int i=0; i<(img->width*img->height); i++){
    count += luminance(img->reds[i], img->greens[i], img->blues[i]);
  }
  double mean = count / ((img->width)*(img->height));
  return mean;
}
