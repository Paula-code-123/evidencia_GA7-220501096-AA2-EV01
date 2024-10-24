import { Routes } from '@angular/router';
import { AuthGuard } from './auth/guards/auth.guards';
import { BuscarListaComponent } from './buscar-lista/buscar-lista.component';
import { CrearListaComponent } from './crear-lista/crear-lista.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { VerListasComponent } from './ver-listas/ver-listas.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'crear', component: CrearListaComponent, canActivate: [AuthGuard] },
  { path: 'ver-todas-listas', component: VerListasComponent, canActivate: [AuthGuard] },
  { path: 'buscar', component: BuscarListaComponent, canActivate: [AuthGuard] },
];
