import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NbButtonModule, NbCardModule, NbDialogService, NbFormFieldModule, NbIconModule, NbInputModule, NbLayoutModule, NbListModule, NbMenuModule, NbOptionModule, NbSelectModule, NbSpinnerModule, NbToastrService, NbTooltipModule } from '@nebular/theme';
import { ListaReproduccionResponse } from '../interfaces/lista-reproduccion-response';
import { PlayListService } from '../services/play-list.service';
import { ModalEliminarComponent } from './modal-eliminar/modal-eliminar.component';

@Component({
  selector: 'app-buscar-lista',
  templateUrl: './buscar-lista.component.html',
  styleUrls: ['./buscar-lista.component.scss'],
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
    NbMenuModule,
    NbListModule,
    NbOptionModule,
    NbSelectModule,
    NbSpinnerModule,
    NbTooltipModule,
  ],
})
export class BuscarListaComponent implements OnInit {

  listaReproduccionResponse!: ListaReproduccionResponse;
  nombresListas: ListaReproduccionResponse[] = [];
  buscarForm!: FormGroup;
  mostrarInfo = false;

  constructor(private playListService: PlayListService,
    private fb: FormBuilder,
    private toastrService: NbToastrService,
    private dialogService: NbDialogService,
  ) { }

  ngOnInit() {
    this.playListService.verListasReproduccion().subscribe((data: ListaReproduccionResponse[]) => {
      this.nombresListas = data;
    }, error => {
      console.error(error);
    }
    );

    this.crearFormulario();
  }

  crearFormulario() {
    this.buscarForm = this.fb.group({
      idLista: ['', Validators.required],
    });
  }

  buscarLista() {
    this.playListService.verListaReproduccionPorId(this.buscarForm.get('idLista')?.value).subscribe((data: ListaReproduccionResponse) => {
      this.mostrarInfo = true;
      this.listaReproduccionResponse = data;
      this.buscarForm.disable();
    }, error => {
      console.error(error);
    }
    );
  }

  limpiar() {
    this.mostrarInfo = false;
    this.buscarForm.reset();
    this.buscarForm.enable();
  }

  eliminarLista() {
    const idLista = this.buscarForm.get('idLista')?.value;

    this.dialogService.open(ModalEliminarComponent).onClose.subscribe((confirmed) => {
      if (confirmed) {
        // Si el usuario confirmó, proceder a eliminar
        this.playListService.eliminarListaReproduccionPorId(idLista).subscribe(
          (response) => {
            this.limpiar();
            this.nombresListas = this.nombresListas.filter(playlist => playlist.id !== idLista);
            this.toastrService.success('Lista de reproducción eliminada', 'Éxito');
          },
          (error) => {
            console.error(error);
            this.toastrService.danger('Error al eliminar la lista de reproducción', 'Error');
          }
        );
      }
    });
  }
}