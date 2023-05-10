import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatPaginatorModule} from '@angular/material/paginator';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { DeleteComponent } from './dialogs/delete/delete.component';
import { AppComponent } from 'src/app/app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatRippleModule } from '@angular/material/core';
import { MatProgressBarModule } from "@angular/material/progress-bar";
import { ProductoService } from '../producto/services/producto.service';
import { AgregarComponent } from './dialogs/agregar/agregar.component';
import { EditarComponent } from './dialogs/editar/editar.component';
import {MatCardModule} from '@angular/material/card';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { EliminarComponent } from './dialogs/eliminar/eliminar.component';
import {MatSelectModule} from '@angular/material/select';
@NgModule({
  declarations: [
    DeleteComponent,
    AgregarComponent,
    EditarComponent,
    EliminarComponent
  ],
  providers: [
    ProductoService
],
exports: [
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatRippleModule,
  FormControl,
  FormsModule
],
  imports: [
    MatProgressBarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatDialogModule,
    FormsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatSortModule,
    MatTableModule,
    MatToolbarModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatCardModule,
    MatDatepickerModule,
    MatCheckboxModule,
    MatSelectModule,
    CommonModule
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ],
  bootstrap: [AppComponent]
})
export class PersonaModule { }
