import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { NbAlertModule, NbButtonModule, NbCardModule, NbFormFieldModule, NbIconModule, NbInputModule, NbLayoutModule, NbMenuModule, NbTooltipModule } from '@nebular/theme';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [
    RouterOutlet,
    NbLayoutModule,
    NbCardModule,
    NbInputModule,
    NbButtonModule,
    NbIconModule,
    NbFormFieldModule,
    NbAlertModule,
    NbMenuModule,
    NbEvaIconsModule,
    NbTooltipModule,
    NgIf,
  ],
})
export class AppComponent {

  constructor(
    private router: Router,
  ) { }

  crearLista() {
    this.router.navigate(["/crear"]);
  }

  verListas() {
    this.router.navigate(["/ver-todas-listas"]);
  }

  buscarLista() {
    this.router.navigate(["/buscar"]);
  }

  inicio() {
    this.router.navigate(["/home"]);
  }

  cerrarSesion() {
    localStorage.clear();
    this.router.navigate(["/login"]);
  }

  isLoginPage(): boolean {
    return this.router.url === '/login'; // O la ruta que tengas para el login
  }
}
