import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NbToastrService } from '@nebular/theme';

@Injectable({
  providedIn: 'root'
})
export class HandleErrorService {

  constructor(private toastrService: NbToastrService) { }

  manejarError(error: HttpErrorResponse): void {
    const opcionesError = !(error.error instanceof ErrorEvent) ? this.obtenerInformacionError(error) : {};

    // Usa un método específico de toastr basado en el estado
    const estado = opcionesError.estado || InformacionError.ERROR_ESTADO_TOAST;
    const mensaje = opcionesError.mensaje || InformacionError.ERROR_DEFECTO;
    const titulo = opcionesError.titulo || InformacionError.ERROR_TITULO;

    if (estado === InformacionError.ERROR_ESTADO_TOAST) {
      this.toastrService.danger(mensaje, titulo);
    } else if (estado === InformacionError.ADVERTENCIA_ESTADO_TOAST) {
      this.toastrService.warning(mensaje, titulo);
    }
    // Aquí puedes añadir más tipos de mensajes si lo deseas
  }

  private obtenerInformacionError(error: HttpErrorResponse): any {
    let opcionesError: any = {};
    if (error.status === HttpStatusCode.Conflict && error.error?.errores?.length > 0) {
      opcionesError.mensaje = error.error.errores[0];
    } else if (error.status === HttpStatusCode.Unauthorized) {
      opcionesError.mensaje = InformacionError.ADVERTENCIA_CREDENCIALES_INCORRECTAS;
    } else {
      opcionesError.mensaje = InformacionError.MAP_ERROR.get(error.status);
    }

    if (opcionesError.mensaje) {
      if (opcionesError.mensaje === InformacionError.ADVERTENCIA_CREDENCIALES_INCORRECTAS) {
        opcionesError.estado = InformacionError.ADVERTENCIA_ESTADO_TOAST;
        opcionesError.titulo = InformacionError.ADVERTENCIA_TITULO;
      } else {
        opcionesError.estado = InformacionError.ERROR_ESTADO_TOAST;
        opcionesError.titulo = InformacionError.ERROR_TITULO;
      }      
    } 

    return opcionesError;
  }
}

class InformacionError {
  static readonly MENSAJE_USER_LOCKED = 'User account is locked';
  static readonly MENSAJE_BAD_CREDENTIALS = 'Bad credentials';
  static readonly ADVERTENCIA_CREDENCIALES_INCORRECTAS = 'El usuario y/o contraseña son incorrectas.';
  static readonly ADVERTENCIA_USUARIO_BLOQUEADO = 'El usuario se encuentra bloqueado por superar el máximo de intentos.';
  static readonly ADVERTENCIA_INTENTOS_MAXIMOS_ALCANZADOS = 'Ha superado el máximo de intentos.';
  static readonly ERROR_DEFECTO = 'Ha ocurrido un error procesando la solicitud.';
  static readonly ERROR_TITULO = 'Error';
  static readonly ERROR_ESTADO_TOAST = 'danger';
  static readonly ADVERTENCIA_ESTADO_TOAST = 'warning';
  static readonly ADVERTENCIA_TITULO = 'Aviso';
  static readonly MAP_ERROR = new Map<number, string>([
    [400, 'Los datos ingresados no cumplen las validaciones.'],
    [401, 'El usuario no cumple con los permisos necesarios.'],
    [403, 'Se ha negado el permiso para ejecutar la acción.']
  ]);
}