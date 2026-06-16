import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from './api.config';

export interface PipelineStep {
  name: string;
  command: string;
  orderIndex: number;
}

export interface CreatePipelineRequest {
  projectId: string;
  name: string;
  description: string;
  branch: string;
  steps: PipelineStep[];
}

export interface PipelineResponse {
  id: string;
  projectId: string;
  name: string;
  description: string;
  branch: string;
  ownerEmail: string;
  status: string;
  steps: PipelineStep[];
  createdAt: string;
  updatedAt: string;
}

export interface PipelineRunResponse {
  id: string;
  pipelineId: string;
  projectId: string;
  pipelineName: string;
  branch: string;
  commitSha: string;
  triggeredBy: string;
  status: string;
  createdAt: string;
  startedAt: string;
  finishedAt: string;
}

@Injectable({ providedIn: 'root' })
export class PipelineService {
  private readonly apiUrl = `${API_BASE_URL}/api/pipelines`;

  constructor(private http: HttpClient) {}

  getPipelines(): Observable<PipelineResponse[]> {
    return this.http.get<PipelineResponse[]>(this.apiUrl);
  }

  createPipeline(request: CreatePipelineRequest): Observable<PipelineResponse> {
    return this.http.post<PipelineResponse>(this.apiUrl, request);
  }

  runPipeline(id: string, body: { branch: string; commitSha: string }): Observable<PipelineRunResponse> {
    return this.http.post<PipelineRunResponse>(`${this.apiUrl}/${id}/runs`, body);
  }

  getRuns(): Observable<PipelineRunResponse[]> {
    return this.http.get<PipelineRunResponse[]>(`${this.apiUrl}/runs`);
  }

  deletePipeline(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
