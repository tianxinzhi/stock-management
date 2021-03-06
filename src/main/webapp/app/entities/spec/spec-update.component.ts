import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISpec, Spec } from 'app/shared/model/spec.model';
import { SpecService } from './spec.service';
import { IAttr } from 'app/shared/model/attr.model';
import { AttrService } from 'app/entities/attr/attr.service';

@Component({
  selector: 'jhi-spec-update',
  templateUrl: './spec-update.component.html'
})
export class SpecUpdateComponent implements OnInit {
  isSaving = false;
  attrs: IAttr[] = [];

  editForm = this.fb.group({
    id: [],
    specName: [],
    specDesc: [],
    verId: [],
    attrs: []
  });

  constructor(
    protected specService: SpecService,
    protected attrService: AttrService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ spec }) => {
      this.updateForm(spec);

      this.attrService.query().subscribe((res: HttpResponse<IAttr[]>) => (this.attrs = res.body || []));
    });
  }

  updateForm(spec: ISpec): void {
    this.editForm.patchValue({
      id: spec.id,
      specName: spec.specName,
      specDesc: spec.specDesc,
      verId: spec.verId,
      attrs: spec.attrs
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const spec = this.createFromForm();
    if (spec.id !== undefined) {
      this.subscribeToSaveResponse(this.specService.update(spec));
    } else {
      this.subscribeToSaveResponse(this.specService.create(spec));
    }
  }

  private createFromForm(): ISpec {
    return {
      ...new Spec(),
      id: this.editForm.get(['id'])!.value,
      specName: this.editForm.get(['specName'])!.value,
      specDesc: this.editForm.get(['specDesc'])!.value,
      verId: this.editForm.get(['verId'])!.value,
      attrs: this.editForm.get(['attrs'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpec>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAttr): any {
    return item.id;
  }

  getSelected(selectedVals: IAttr[], option: IAttr): IAttr {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
