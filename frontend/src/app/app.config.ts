import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ApplicationConfig } from '@angular/core';
import { BrowserAnimationsModule, provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { NbDialogModule, NbIconModule, NbLayoutModule, NbMenuModule, NbSidebarModule, NbThemeModule, NbToastrModule } from '@nebular/theme';
import { tokenInterceptor } from './@share/interceptors/token.interceptor';
import { routes } from './app.routes';
import { ErrorsInterceptor } from './@share/services/errors-interceptor';


export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([tokenInterceptor, ErrorsInterceptor.interceptor])
    ),
    provideAnimations(),
    // Verificar si los proveedores existen
    NbThemeModule.forRoot({ name: 'default' }).providers || [],
    NbSidebarModule.forRoot().providers || [],
    NbMenuModule.forRoot().providers || [],
    NbLayoutModule,
    NbToastrModule.forRoot().providers || [],
    NbIconModule,
    BrowserAnimationsModule,
    NbDialogModule.forRoot().providers || [],
  ],
};
