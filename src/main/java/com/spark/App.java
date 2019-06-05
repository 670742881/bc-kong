package com.spark;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }


    private   App(){};
    public final static App a=new App();
    public final static App getInstance(){
        return a;
    }
}


