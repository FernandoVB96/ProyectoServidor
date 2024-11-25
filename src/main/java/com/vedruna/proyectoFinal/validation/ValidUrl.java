package com.vedruna.proyectoFinal.validation;

// Importaciones necesarias para la validación y la creación de anotaciones personalizadas.
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Esta anotación puede aplicarse a campos, métodos, parámetros o a otras anotaciones.
// @Target define los lugares donde la anotación puede ser usada.
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
// Indica que esta anotación estará disponible en tiempo de ejecución, lo cual es necesario para el sistema de validación.
@Retention(RetentionPolicy.RUNTIME)
// Vincula esta anotación con la clase UrlValidator, que será responsable de realizar la validación de la URL.
@Constraint(validatedBy = UrlValidator.class)
public @interface ValidUrl {
    
    // Mensaje que se usará en caso de que la validación falle. Se puede personalizar.
    String message() default "Invalid URL format"; 
    
    // Permite agrupar validaciones. Por ahora está vacío, pero puede ser usado para agrupar validaciones por contexto.
    Class<?>[] groups() default {}; 
    
    // Permite asociar información adicional a la validación. También está vacío por defecto.
    Class<? extends Payload>[] payload() default {}; 
}
