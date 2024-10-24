import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NbAlertModule, NbButtonModule, NbCardModule, NbCheckboxModule, NbFormFieldModule, NbIconModule, NbInputModule, NbLayoutModule, NbMenuModule, NbOptionModule, NbSidebarModule, NbToastrModule, NbToastrService } from '@nebular/theme';
import { Cancion } from '../interfaces/cancion';
import { ListaReproduccionRequest } from '../interfaces/lista-reproduccion-request';
import { PlayListService } from '../services/play-list.service';

@Component({
  selector: 'app-crear-lista',
  templateUrl: './crear-lista.component.html',
  styleUrls: ['./crear-lista.component.scss'],
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
    NbSidebarModule,
    FormsModule,
    NbOptionModule,
    NbToastrModule,
  ],
})
export class CrearListaComponent implements OnInit {
  canciones: Cancion[] = [];
  cancionesSeleccionadas: Cancion[] = [];
  listaForm!: FormGroup;

  constructor(private fb: FormBuilder,
    private playListService: PlayListService,
    private toastrService: NbToastrService,
  ) { }

  ngOnInit() {
    this.crearFormulario();
    this.loadCanciones();
  }

  private crearFormulario() {
    this.listaForm = this.fb.group({
      nombre: ["", Validators.required],
      descripcion: ["", Validators.required],
      canciones: this.fb.array([])
    });
  }

  private loadCanciones() {
    this.playListService.verCanciones().subscribe((data: Cancion[]) => {
      this.canciones = data;
      this.addCancionesToFormArray();
    }, error => {
      console.error(error);
    });
  }

  private addCancionesToFormArray() {
    this.canciones.forEach(cancion => {
      const cancionFormGroup = this.fb.group({
        seleccionado: [false],
        titulo: [cancion.titulo, Validators.required],
        artista: [cancion.artista, Validators.required],
        idCancion: [cancion.id]
      });
      this.cancionesFormArray.push(cancionFormGroup);
    });
  }

  get cancionesFormArray() {
    return this.listaForm.get('canciones') as FormArray;
  }

  agregarLista() {
    const cancionesSeleccionadas = this.cancionesFormArray.controls
      .filter(control => control.get('seleccionado')?.value);

    if (this.listaForm.invalid) {
      this.toastrService.warning('Faltan campos requeridos por diligenciar', 'Advertencia');
      return;
    }

    if (cancionesSeleccionadas.length === 0) {
      this.toastrService.warning('Debe seleccionar al menos una canción.', 'Advertencia');
      return;
    }

    const cancionesIds: number[] = cancionesSeleccionadas.map(control => control.get('idCancion')?.value);

    const listaReproduccionRequest: ListaReproduccionRequest = {
      nombre: this.listaForm.get('nombre')?.value,
      descripcion: this.listaForm.get('descripcion')?.value,
      cancionesIds: cancionesIds,
    };

    this.playListService.crearListaReproduccion(listaReproduccionRequest).subscribe((res) => {
      this.toastrService.success('Lista de reproducción guardada exitosamente!', 'Éxito');
      this.limpiarFormulario();
    }, error => {
      this.toastrService.danger('Ocurrió un error al guardar la lista de reproducción', 'Error');
      console.error(error);
    });
  }

  limpiarFormulario() {
    this.listaForm.reset();
    this.cancionesFormArray.clear();
    this.loadCanciones();
  }
}
