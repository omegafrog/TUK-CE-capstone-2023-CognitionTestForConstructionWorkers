package com.tukorea.cogTest.controller;

import com.tukorea.cogTest.domain.NotPassedCount;
import com.tukorea.cogTest.domain.NotPassedRepository;
import com.tukorea.cogTest.dto.*;
import com.tukorea.cogTest.paging.Page;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.service.*;
import jakarta.annotation.Nullable;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.tukorea.cogTest.paging.Page.getPage;
import static com.tukorea.cogTest.response.ResponseUtil.*;

@Slf4j
@RequestMapping("/admin")
@ResponseBody
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;
    private final SubjectService subjectService;
    private final NotPassedRepository notPassedCountRepository;

    /**
     * Admin으로 인증된 유저가 특정 피험자의 정보를 열람한다.
     *
     * @param id : 피험자의 아이디
     * @return {
     * statusCode : 200,
     * msg : Get subject success,
     * results : {
     * 검색한 피험자 객체
     * }
     * }
     */
    @GetMapping("/subject/{id}")
    public ResponseEntity<Map<String, Object>> getSubject(
            @PathVariable(name = "id") Long id) {
        try {
            SubjectDTO foundedSubject = subjectService.findSubject(id);
            Map<String, Object> result = new ConcurrentHashMap<>();

            result.put("subject", foundedSubject);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get subject success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.BAD_REQUEST, "Wrong Requset", null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/np_count")
    public ResponseEntity<Map<String, Object>> getNonPassedCount(
            Principal principal,
            @RequestParam int curPageNum,
            @RequestParam int contentPerPage
    ) {
        try {
            String username = principal.getName();
            AdminDTO byUsername = adminService.findByUsername(username);
            Long fieldId = byUsername.getField().getId();
            List<NotPassedCount> byFieldId = notPassedCountRepository.findByField_id(fieldId);
            List<NotPassedCountDto> dto = byFieldId.stream().map(NotPassedCount::toDto).collect(Collectors.toList());
            Page page = Page.getPage(curPageNum, contentPerPage, "notPassedCount",dto, -1 );
            Collections.reverse((List<NotPassedCountDto>)page.getContents().get("notPassedCount"));
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("NotPassedCount", dto);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get npCount success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    /**
     * 관리자가 관리하는 피험자들을 가져온다.
     *
     * @param curPageNum     : 현재 페이지 번호
     * @param contentPerPage : 페이지당 컨텐츠 갯수
     * @return {
     * statusCode : 200,
     * msg : "Get subjects success",
     * page : {
     * curPageNum : 현재 페이지 번호,
     * allPageNum : 전체 페이지 개수,
     * startPageNum : 시작 페이지 번호,
     * endPageNum : 끝 페이지 번호,
     * contentPerPage : 페이지 당 보여줄 컨텐츠,
     * prev : 이전 화살표 버튼 ( boolean )
     * next : 다음 화살표 버튼 ( boolean )
     * }
     * }
     */
    @GetMapping("/subjects")
    public ResponseEntity<Map<String, Object>> getSubjects(
            Principal principal,
            @RequestParam int curPageNum,
            @RequestParam int contentPerPage
    ) {
        try {
            String username = principal.getName();
            AdminDTO byUsername = adminService.findByUsername(username);
            Long fieldId = byUsername.getField().getId();
            List<SubjectDTO> subjectList = subjectService.findSubjectInField(fieldId);

            Page page = getPage(curPageNum, contentPerPage, "subject", subjectList);

            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("page", page);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Get subjects success", result), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    /**
     * 피험자의 개인정보를 추가한다.
     *
     * @param mode     "multi" : json 형식의 body로 추가
     *                 "sole" : form 형식의 값으로 추가
     * @param subjects : json으로 전달된 피험자 정보
     * @return {
     * statusCode : 200,
     * msg : Add subject by &lt;mode&gt; success.
     * results : {
     * 추가한 피험자 객체
     * }
     * }
     */
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class WorkerInput {
        private String mode;
        private List<SubjectForm> subjects;
    }

    @PostMapping(value = "/subject")
    public ResponseEntity<Map<String, Object>> addWorker(
            @RequestBody @Nullable WorkerInput workerInput,
            Principal principal
    ) {
        try {
            String mode = workerInput.getMode();
            List<SubjectForm> subjects = workerInput.getSubjects();
            String username = principal.getName();
            Long adminId = adminService.findByUsername(username).getField().getId();
            Map<String, Object> body = switch (mode) {
                case "multi" -> adminService.addMultiWorkers(adminId, subjects);
                case "sole" -> adminService.addSoleWorker(adminId, subjects.get(0));
                default -> throw new IllegalArgumentException("잘못된 mode parameter 입니다." + mode);
            };
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Add subject by " + mode + " success.",
                    body), HttpStatus.OK);
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    /**
     * 피험자의 정보를 수정한다.
     *
     * @param id          : 피험자의 id
     * @param subjectForm : 피험자 정보 폼
     * @return {
     * statusCode : 200,
     * msg : update subject success.
     * results : {
     * 수정한 피험자 객체
     * }
     * }
     */
    @PostMapping("/subject/{id}")
    public ResponseEntity<Map<String, Object>> updateWorker(
            @PathVariable Long id,
            SubjectForm subjectForm,
            Principal principal
    ) {
        try {

            // 피험자 정보 업데이트
            SubjectDTO updatedSubject = subjectService.update(id, subjectForm);
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("subject", updatedSubject);
            Map<String, Object> body = setResponseBody(HttpStatus.OK, "Update subject success", result);
            return new ResponseEntity<>(body, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }

    /**
     * 피험자를 삭제한다. 피험자와 포함된 시험 결과도 삭제한다.
     *
     * @param id 노동자의 id
     * @return
     */
    @DeleteMapping("/subject/{id}")
    public ResponseEntity<Map<String, Object>> deleteWorker(
            @PathVariable Long id
    ) {
        try {
            subjectService.delete(id);
            return new ResponseEntity<>(ResponseUtil.setResponseBody(HttpStatus.OK, "Delete subject success", null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.returnWrongRequestErrorResponse(e);
        } catch (RuntimeException e) {
            return ResponseUtil.setInternalErrorResponse(e);
        }
    }
}
