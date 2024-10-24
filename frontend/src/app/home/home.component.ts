import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { NbAlertModule, NbButtonModule, NbCardModule, NbCheckboxModule, NbFormFieldModule, NbIconModule, NbInputModule, NbLayoutModule, NbListModule, NbMenuModule, NbSidebarModule } from '@nebular/theme';
import { SharedModule } from '../@share/share.module';
import { Cancion } from '../interfaces/cancion';
import { PlayListService } from '../services/play-list.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
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
    NbListModule,
    NbSidebarModule,
    SharedModule,
  ],
})
export class HomeComponent implements OnInit {
  canciones: Cancion[] = [];

  constructor(private playListService: PlayListService,
  ) { }

  ngOnInit() {
    this.playListService.verCanciones().subscribe((data: Cancion[]) => {
      this.canciones = data;
    }, error => {
      console.error(error);
    });
  }
}

