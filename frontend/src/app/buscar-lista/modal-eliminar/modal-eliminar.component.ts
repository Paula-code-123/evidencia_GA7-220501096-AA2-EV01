import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { NbButtonModule, NbCardModule, NbDialogRef, NbLayoutModule } from '@nebular/theme';

@Component({
  selector: 'app-modal-eliminar',
  templateUrl: './modal-eliminar.component.html',
  styleUrls: ['./modal-eliminar.component.css'],
  standalone: true,
  imports: [
    NbCardModule,
    CommonModule,
    ReactiveFormsModule,
    NbLayoutModule,
    NbButtonModule,
  ],
})
export class ModalEliminarComponent {

  constructor(private ref: NbDialogRef<ModalEliminarComponent>) { }

  cancel() {
    this.ref.close(false);
  }

  confirm() {
    this.ref.close(true);
  }
}
