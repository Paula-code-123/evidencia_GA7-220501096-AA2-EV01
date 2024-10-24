import { inject, Injectable } from "@angular/core";
import { HandleErrorService } from "./handle-error.service";
import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http";
import { catchError, throwError } from "rxjs";

@Injectable()
export class ErrorsInterceptor {
    private static ENDPOINTS_WHITELIST = ['/oauth/token'];
  
    static interceptor: HttpInterceptorFn = (req, next) => {
      // Inyecta el servicio utilizando el método inject
      const handleErrorService = inject(HandleErrorService);
      
      return next(req).pipe(
        catchError((error: HttpErrorResponse) => {
          if (!ErrorsInterceptor.ENDPOINTS_WHITELIST.includes(req.url)) {
            handleErrorService.manejarError(error);
          }
          return throwError(() => error);
        })
      );
    };
}