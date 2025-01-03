#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <pthread.h>
#include "hw5.h"

// ======== utilities ========

// some of these have mock implementations for phase1
// replace mock implementations with real ones

int_list *il_cons(int f, int_list *r)
{
  int_list *current = r;
  if (current == NULL){
    int_list *new_il = malloc(sizeof(int_list));
    if(new_il==NULL){
      fprintf(stderr, "Unable to allocate memory. \n");
      exit(1);
    }
    new_il->first = f;
    new_il->rest = NULL;
    return new_il;
  }
  while(current->rest != NULL){
    current = current->rest;
  }
  current->rest = malloc(sizeof(int_list));
  if(current->rest==NULL){
    fprintf(stderr, "Unable to allocate memory. \n");
    exit(1);
  }
  current->rest->first = f;
  current->rest->rest = NULL;
  return r;
}

void il_free(int_list *ns)
{
  int_list *current = ns;
  int_list *next;
  while(current != NULL){
    next = current->rest;
    free(current);
    current = next;
  }
}

void il_show(int_list *ns)
{
  int_list *current = ns;
  while(current != NULL){
    if (current->rest != NULL){
      printf("'%d', ", current->first);
    }
    else{
      printf("'%d'", current->first);
    }
    current = current->rest;
  }
  printf("\n");
}

char_list *cl_cons(char f, char_list *r)
{
  char_list *current = r;
  if (current == NULL){
    char_list *new_cl = malloc(sizeof(char_list));
    if(new_cl==NULL){
      fprintf(stderr, "Unable to allocate memory. \n");
      exit(1);
    }
    new_cl->first = f;
    new_cl->rest = NULL;
    return new_cl;
  }
  while(current->rest != NULL){
    current = current->rest;
  }
  current->rest = malloc(sizeof(char_list));
  if(current->rest==NULL){
    fprintf(stderr, "Unable to allocate memory. \n");
    exit(1);
  }
  current->rest->first = f;
  current->rest->rest = NULL;
  return r;
}

void cl_free(char_list *cs)
{
  char_list *current = cs;
  char_list *next;
  while(current != NULL){
    next = current->rest;
    free(current);
    current = next;
  }
}

void cl_show(char_list *cs)
{
  char_list *current = cs;
  while(current != NULL){
    if (current->rest != NULL){
      printf("'%c', ", current->first);
    }
    else{
      printf("'%c'", current->first);
    }
    current = current->rest;
  }
  printf("\n");
}

str_list *sl_cons(char *f, str_list *r)
{
  str_list *current = r;
  if (current == NULL){
    str_list *new_sl = malloc(sizeof(str_list));
    if(new_sl==NULL){
      fprintf(stderr, "Unable to allocate memory. \n");
      exit(1);
    }
    new_sl->first = f;
    new_sl->rest = NULL;
    return new_sl;
  }
  while(current->rest != NULL){
    current = current->rest;
  }
  current->rest = malloc(sizeof(str_list));
  if(current->rest==NULL){
    fprintf(stderr, "Unable to allocate memory. \n");
    exit(1);
  }
  current->rest->first = f;
  current->rest->rest = NULL;
  return r;
}

void sl_free(str_list *ss)
{
  str_list *current = ss;
  str_list *next;
  while(current != NULL){
    next = current->rest;
    free(current);
    current = next;
  }
}

void sl_show(str_list *ss)
{
  str_list *current = ss;
  while(current != NULL){
    if (current->rest != NULL){
      printf("'%s', ", current->first);
    }
    else{
      printf("'%s'", current->first);
    }
    current = current->rest;
  }
  printf("\n");
}

point_list *pl_cons(point f, point_list *r)
{
  point_list *current = r;
  if (current == NULL){
    point_list *new_pl = malloc(sizeof(point_list));
    if(new_pl==NULL){
      fprintf(stderr, "Unable to allocate memory. \n");
      exit(1);
    }
    new_pl->first = f;
    new_pl->rest = NULL;
    return new_pl;
  }
  while(current->rest != NULL){
    current = current->rest;
  }
  current->rest = malloc(sizeof(point_list));
  if(current->rest==NULL){
    fprintf(stderr, "Unable to allocate memory. \n");
    exit(1);
  }
  current->rest->first = f;
  current->rest->rest = NULL;
  return r;
}

void pl_free(point_list *ps)
{
  point_list *current = ps;
  point_list *next;
  while(current != NULL){
    next = current->rest;
    free(current);
    current = next;
  }
}

void pl_show(point_list *ps)
{
  point_list *current = ps;
  while(current != NULL){
    if (current->rest != NULL){
      printf("(%f, %f), ", current->first.x, current->first.y);
    }
    else{
      printf("(%f, %f)", current->first.x, current->first.y);
    }
    current = current->rest;
  }
  printf("\n");
}

// ======== operations ========

char *concat(char *s1, char *s2)
{
  if(s1==NULL || s2==NULL){
    fprintf(stderr, "Null arguments. \n");
    exit(1);
  }
  char *new_str = malloc(strlen(s1) + strlen(s2) + 1);
  if(new_str==NULL){
    fprintf(stderr, "Unable to allocate memory. \n");
    exit(1);
  }
  int new_str_i=0;
  for(int i=0; s1[i] != '\0'; i++){
    new_str[new_str_i] = s1[i];
    new_str_i++;
  }
  for(int i=0; s2[i] != '\0'; i++){
    new_str[new_str_i] = s2[i];
    new_str_i++;
  }
  new_str[new_str_i] = '\0';
  return new_str;
}

double integral(double(*f)(double), double a, double b, double dx)
{
  double r_sum = 0.0;
  for (double i=a; i<=b-dx; i+=dx){
    double y_val = f(i);
    r_sum += (y_val * dx);
  }
  return r_sum;
}

char_list *caps(char *s)
{
  if (s==NULL){
    fprintf(stderr, "Null arguments. \n");
    exit(1);
  }
  char_list *all_caps = NULL;
  for(int i=0; i<strlen(s); i++){
    if (s[i] >= 'A' && s[i] <= 'Z'){
      all_caps = cl_cons(s[i], all_caps);
    }
  }
  return all_caps;
}

char *bitstring(unsigned char b)
{
  char *b_str = malloc(9);
  if(b_str==NULL){
    fprintf(stderr, "Unable to allocate memory. \n");
    exit(1);
  }
  for(int i=0; i<8; i++){
    unsigned char b_num = (b>>(7-i)) & 1;
    if(b_num == 0){
      b_str[i] = '0';
    }
    else{
      b_str[i] = '1';
    }
  }
  b_str[8] = '\0';
  return b_str;
}

double distance(point *p, point *q)
{
  double x_dist = fabs(p->x - q->x);
  double y_dist = fabs(p->y - q->y);
  double dist = sqrt(pow(x_dist,2) + pow(y_dist,2));
  return dist;
}

int len_pl(point_list *pts)
{
  point_list *current = pts;
  int count = 0;
  while(current != NULL){
    count++;
    current = current->rest;
  }
  return count;
}

double polygon_perim(point_list *polygon)
{
  if(len_pl(polygon) < 3){
    fprintf(stderr, "Not a polygon. \n");
    exit(1);
  }
  point_list *current = polygon;
  double dist = 0.0;
  point *prior_p = NULL;
  while(current != NULL){
    if(prior_p != NULL){
      dist += distance(prior_p, &(current->first));
    }
    prior_p = &(current->first);
    current = current->rest;
  }
  if(prior_p != NULL){
    dist += distance(prior_p, &(polygon->first));
  }
  return dist;
}

double sum_x(point_list *dataset)
{
  point_list *current = dataset;
  double count = 0.0;
  while(current != NULL){
    count += current->first.x;
    current = current->rest;
  }
  return count;
}

double sum_x2(point_list *dataset)
{
  point_list *current = dataset;
  double count = 0.0;
  while(current != NULL){
    count += pow(current->first.x,2);
    current = current->rest;
  }
  return count;
}

double sum_y(point_list *dataset)
{
  point_list *current = dataset;
  double count = 0.0;
  while(current != NULL){
    count += current->first.y;
    current = current->rest;
  }
  return count;
}

double sum_xy(point_list *dataset)
{
  point_list *current = dataset;
  double count = 0.0;
  while(current != NULL){
    count += (current->first.x) * (current->first.y);
    current = current->rest;
  }
  return count;
}

lineq linreg(point_list *dataset)
{
  if(len_pl(dataset) < 2){
    fprintf(stderr, "Too few points. \n");
    exit(1);
  }
  double m_num = len_pl(dataset)*sum_xy(dataset) - sum_x(dataset)*sum_y(dataset);
  double b_num = sum_y(dataset)*sum_x2(dataset) - sum_x(dataset)*sum_xy(dataset);
  double mb_den = len_pl(dataset)*sum_x2(dataset) - pow(sum_x(dataset),2);
  double m = m_num / mb_den;
  double b = b_num / mb_den;
  lineq fitted = {m,b};
  return fitted;
}

unsigned int total_length(str_list *strings)
{
  str_list *current_p = strings;
  unsigned int len = 0;
  while(current_p != NULL){
    len += strlen(current_p->first);
    current_p = current_p->rest;
  }
  return len;
}

str_list *split(char sep, char *s)
{
  str_list *sp_str = NULL;
  int prior_ind = -1;
  for(int i=0; i<=strlen(s); i++){
    if(sep==s[i] || i==strlen(s)){
      char *temp_str = malloc(i-(prior_ind+1)+1);
      if (temp_str == NULL) {
        fprintf(stderr, "Unable to allocate memory. \n");
        exit(1);
      }
      int temp_ind = 0;
      for(int j=prior_ind+1; j<i; j++){
          temp_str[temp_ind] = s[j];
          temp_ind++;
      }
      temp_str[temp_ind] = '\0';
      sp_str = sl_cons(temp_str, sp_str);
      prior_ind = i;
    }
  }
  return sp_str;
}

unsigned int num_strs(str_list *s)
{
  str_list *current = s;
  unsigned int count = 0;
  while(current != NULL){
    count++;
    current = current->rest;
  }
  return count;
}

char *join(char j, str_list *strings)
{
  unsigned int strs_len = total_length(strings);
  unsigned int n_strs = num_strs(strings);
  char *joined = malloc(strs_len + n_strs-1 + 1);
  if (joined == NULL){
    fprintf(stderr, "Unable to allocate memory. \n");
    exit(1);
  }
  str_list *current = strings;
  int j_ind = 0;
  while(current != NULL){
    for(int i=0; i<strlen(current->first); i++){
      joined[j_ind] = current->first[i];
      j_ind++;
    }
    if(j_ind != (strs_len + n_strs-1)){
      joined[j_ind] = j;
      j_ind++;
    }
    current = current->rest;
  }
  joined[j_ind] = '\0';
  return joined;
}

struct sqn_args{
  int *nums;
  int start;
  int end;
};

void *square_nums_void(void *args)
{
  struct sqn_args *args2 = (struct sqn_args*) args;
  int len = args2->end - args2->start;
  int* sq_arr = (int*) malloc(sizeof(int)*len);
  int sq_ind = 0;
  for(int i=args2->start; i<args2->end; i++){
    sq_arr[sq_ind] = args2->nums[i] * args2->nums[i];
    sq_ind++;
  }
  return (void*) sq_arr;
}

int *square_nums(int *nums, int size, int n_threads)
{
  int *new_arr = malloc(sizeof(int) * size);
  int th_size = size / n_threads;
  int rem = size % n_threads;
  pthread_t all_threads[n_threads];
  struct sqn_args *args_array = malloc(sizeof(struct sqn_args) * n_threads);
  for(int i=0; i<n_threads; i++){
    int start = i * th_size;
    int end = (i+1) * th_size;
    if(i < rem){
      end++;
    }
    args_array[i].nums = nums;
    args_array[i].start = start;
    args_array[i].end = end;
    pthread_create(&all_threads[i], NULL, &square_nums_void, &args_array[i]);
  }
  for(int i=0; i<n_threads; i++){
    int *result;
    pthread_join(all_threads[i], (void**)&result);
    for(int j=0; j<args_array[i].end - args_array[i].start; j++){
      new_arr[args_array[i].start + j] = result[j];
    }
    free(result);
  }
  free(args_array);
  return new_arr;
}

unsigned int luminance(struct rgb c)
{
    unsigned int lum = floor(0.2126*c.red + 0.7152*c.green + 0.0722*c.blue);
    return lum;
}

struct rgb grayscale(struct rgb c)
{
    struct rgb gray;
    unsigned int lum = luminance(c);
    gray.red = lum;
    gray.green = lum;
    gray.blue = lum;
    return gray;
}

void grayscale_seq(struct image *img)
{
  int len = img->width * img->height;
  for(int i=0; i<len; i++){
      img->pixels[i] = grayscale(img->pixels[i]);
  }
}

void *grayscale_seq_void(void *img)
{
    struct image *im = (struct image*) img;
    grayscale_seq(im);
    free(img);
    return NULL;
}

struct sliced_image{
  struct image *img;
  int start;
  int end;
};

void grayscale_par(struct image *img, unsigned int n_threads)
{
    int img_size = img->height * img->width;
    int th_size = img_size / n_threads;
    int rem = img_size % n_threads;
    pthread_t all_threads[n_threads];
    for (int i=0; i<n_threads; i++){
        int start = i * th_size;
        int end = (i+1) * th_size;
        if(i < rem){
          end++;
        }
        struct sliced_image *temp_img = malloc(sizeof(struct sliced_image));
        temp_img->img = img;
        temp_img->start = start;
        temp_img->end = end;
        pthread_create(&all_threads[i], NULL, &grayscale_seq_void, temp_img);
    }
    for (int i=0; i<n_threads; i++){
        pthread_join(all_threads[i], NULL);
    }
}