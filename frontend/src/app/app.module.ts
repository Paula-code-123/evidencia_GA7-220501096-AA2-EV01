import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NbLayoutModule, NbMenuModule, NbToastrModule } from '@nebular/theme';
import { SharedModule } from './@share/share.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VerListasComponent } from './ver-listas/ver-listas.component';
import { BuscarListaComponent } from './buscar-lista/buscar-lista.component';

@NgModule({
    declarations: [		
        AppComponent,
      VerListasComponent,
      BuscarListaComponent
   ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        SharedModule,
        ReactiveFormsModule,
        NbMenuModule.forRoot(),
        NbLayoutModule,
        NbToastrModule.forRoot(),
    ],
    providers: [
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }

