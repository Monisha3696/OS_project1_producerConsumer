#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include<string.h>
void osassign(int i,int n);
int main(int argc, char *argv[])
{
char output[30];
int n;
pid_t child_pid,parent_pid; 
n=atoi(argv[1]);    // command line arguments
if(argc!=2)
{
    printf("Invalid arguments/n");
    return 0;
}
 if(n<=0 || n>=40) {// Checks for invalid input
 printf("Invalid Input/n");
 return 0;
 }
          if ((parent_pid = fork()) == 0)     // Creation of Process through first fork
         {     
          sprintf(output,"\nFrom Child 1 init n= %d,\t",n);
          printf("%s",output);
          osassign(n,1); // Calls function
          
        }
        
       else{ 
             if((child_pid = fork()) == 0)    //Creation of Process through Second fork
              {
                  sprintf(output,"\nFrom Child 2 init n= %d,\t",n+3);
          printf("%s",output);
                 osassign(n+3,2); // Calls function
                   exit(0);
                }
             else{
                      wait(NULL);
                      wait(NULL);
                      sprintf(output,"\nChildren Complete\n");
                      printf("%s",output);
                    }
       }
        
 return 0;
}
void osassign(int i,int c)  
 {
 char op[30];
  while(i >0){
      
   if(i%2 == 0)
        i =i/2;
   else
       i = 3*i + 1;
       sprintf(op,"From Child %d n= %d,\t",c,i);
       printf("%s",op);// Printing the respective sequence
       if(i==1)
       return;
 }

 
return;
}

