import { Component, OnInit } from '@angular/core';
import { ListaReproduccionResponse } from '../interfaces/lista-reproduccion-response';
import { PlayListService } from '../services/play-list.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { NbAccordionModule, NbButtonModule, NbCardModule, NbFormFieldModule, NbIconModule, NbInputModule, NbLayoutModule, NbListModule, NbMenuModule } from '@nebular/theme';

@Component({
  selector: 'app-ver-listas',
  templateUrl: './ver-listas.component.html',
  styleUrls: ['./ver-listas.component.scss'],
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
    NbAccordionModule,
  ],
})
export class VerListasComponent implements OnInit {

  listaReproduccionResponse: ListaReproduccionResponse[] = [];

  constructor(private playListService: PlayListService,) { }

  ngOnInit() {
    this.playListService.verListasReproduccion().subscribe((data: ListaReproduccionResponse[]) => {
      this.listaReproduccionResponse = data;
    }, error => {
      console.error(error);
    }
    );
  }

}
