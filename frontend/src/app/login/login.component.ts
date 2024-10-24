import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NbAlertModule, NbButtonModule, NbCardModule, NbCheckboxModule, NbFormFieldModule, NbIconModule, NbInputModule, NbLayoutModule, NbMenuModule } from '@nebular/theme';
import { SharedModule } from '../@share/share.module';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NbLayoutModule,
    NbCardModule,
    NbInputModule,
    NbButtonModule,
    NbIconModule,
    NbFormFieldModule,
    NbAlertModule,
    NbCheckboxModule,
    NbMenuModule,
    SharedModule,
  ],
})
export class LoginComponent implements OnInit {

  signInForm!: FormGroup;

  constructor(
    public fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.signInForm = this.fb.group({
      usuario: ["", [Validators.required]],
      clave: ["", Validators.required],
    });
  }

  login(): void {
    if (this.signInForm.invalid) {
      return
    }
    const usuario = this.signInForm.get('usuario')?.value;
    const clave = this.signInForm.get('clave')?.value;

    this.authService.login(usuario, clave).subscribe(
      response => {
        this.router.navigate(['/home']);
      },
      error => {
        console.error('Error', error);
      }
    );

  }
}
