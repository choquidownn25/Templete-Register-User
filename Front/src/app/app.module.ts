import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { HomeComponent } from './pages/home/home.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AboutComponent } from './pages/about/about.component';
import { HelpComponent } from './pages/help/help.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { ProductoComponent } from './pages/producto/producto.component';
import { AddComponent } from './pages/producto/dialogs/add/add.component';
import { EditComponent } from './pages/producto/dialogs/edit/edit.component';
import { DeleteComponent } from './pages/producto/dialogs/delete/delete.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { ProductoService } from './pages/producto/services/producto.service';
import { HttpClientModule } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatRippleModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { PersonaComponent } from './pages/persona/persona.component';
import { PersonaService } from './pages/persona/service/persona.service';
import { AgregarComponent } from './pages/persona/dialogs/agregar/agregar.component';
import { EditarComponent } from './pages/persona/dialogs/editar/editar.component';
import { MatCardModule } from '@angular/material/card';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { EliminarComponent } from './pages/persona/dialogs/eliminar/eliminar.component';

import { MatNativeDateModule } from '@angular/material/core';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { EmpleadoComponent } from './pages/empleado/empleado.component';
import { EmpAddEditComponent } from './pages/empleado/emp-add-edit/emp-add-edit.component';
import { EmpEditComponent } from './pages/empleado/emp-edit/emp-edit.component';
import { EmpDeleteComponent } from './pages/empleado/emp-delete/emp-delete.component';
import { EmpDetailComponent } from './pages/empleado/emp-detail/emp-detail.component';
import { SignupComponent } from './pages/sessions/signup/signup.component';
import { SigninComponent } from './pages/sessions/signin/signin.component';


@NgModule({
  declarations: [AppComponent, HomeComponent, ProfileComponent, AboutComponent, HelpComponent, NotFoundComponent, ProductoComponent, EmpleadoComponent,AddComponent, EditComponent, DeleteComponent, PersonaComponent, AgregarComponent, EditarComponent, EliminarComponent, EmpAddEditComponent, EmpEditComponent, EmpDeleteComponent, EmpDetailComponent, SignupComponent, SigninComponent],
  imports: [
    MatNativeDateModule,
    MatRadioModule,
    MatSelectModule,
    MatSnackBarModule,
    MatProgressBarModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    FormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSortModule,
    MatTableModule,
    MatToolbarModule,
    MatPaginatorModule,
    HttpClientModule,
    MatDialogModule,
    MatCardModule,
    MatDatepickerModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    MatProgressBarModule
  ],
 providers: [
  ProductoService,
  PersonaService
  ],
  exports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule,
  ],
  
  bootstrap: [AppComponent]
})
export class AppModule { }
