package com.project.ResumeBuilder.exception;

public class ResourceAlreadyExistsException extends RuntimeException{
    public  ResourceAlreadyExistsException (final String message){
        super(message);
    }
}
