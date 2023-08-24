using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

//수정 및 추가할 부분//////////////////////////////////
//23/4/1 v1.5 수정완료내역
//각 라운드 이동 수정완료
//타이머 수정완료
//23/8/8
//가상조이스틱 추가완료
//


public class Maze_move : MonoBehaviour
{
    public float CH_speed; //캐릳터 이동속도
    Vector3 lookDirection;
    public static int Trigger_Count; // 메인게임 충돌카운트
    
    public static bool round1_end = false; // 게임1 종료확인
    public static bool round2_end = false; //게임2 종료확인
    public static bool round3_end = false; // 게임3 종료확인

    public static bool isStage1 = true;
    public static bool isStage2;
    public static bool isStage3;

    public static bool Camera1 = false;
    public static bool Camera2 = false;

    public static int timeout_count;
    public static bool maze_result;
    //public static bool tutorial_end; // 튜토리얼 종료확인

    void Start()
    {
        CH_speed = 2.5f;
        Trigger_Count = 0;
        timeout_count = 0;

        isStage2 = false;
        isStage3 = false;
        maze_result = false;
    }

    // Update is called once per frame
    void Update()
    {
        //Debug.Log(timeout_count);
        //move(); //움직임 제어
        if(isStage1 == true) Stage1();
        if(isStage2 == true) Stage2(); //1라운드 완료시 라운드 이동
        if(isStage3 == true) Stage3(); //2라운드 완료시 라운드 이동
        //if(tutorial_end == true) Tutorial(); //튜토리얼 종료함수
    }

    private void move() //캐릭터 제어함수, 캐릭터를 회전시켜 자연스럽게 이동하도록 변경
    {
        if (Input.GetKey(KeyCode.UpArrow) || Input.GetKey(KeyCode.DownArrow) || 
            Input.GetKey(KeyCode.LeftArrow) || Input.GetKey(KeyCode.RightArrow)){

            float xx = Input.GetAxisRaw("Vertical");
            float zz = Input.GetAxisRaw("Horizontal");
            lookDirection = xx * Vector3.forward + zz * Vector3.right;

            this.transform.rotation = Quaternion.LookRotation(lookDirection);
            this.transform.Translate(Vector3.forward * CH_speed * Time.deltaTime);
        }
    }

    private void OnTriggerEnter(Collider other) //충돌 감지
    {
        //if(other.tag == "tutorial_trigger") Tutorial_Count++; //튜토리얼 충돌 카운트
        if (other.tag == "mazer_trigger") Trigger_Count++; //트리거 충돌시 카운트+1

        if (other.tag == "maze_end1") round1_end = true;
        if (other.tag == "maze_end2") round2_end = true;
        if(other.tag == "maze_end3") round3_end = true;
        //if( other.tag == "tuto_end") tutorial_end = true;
    }

    //private void Tutorial() //튜토리얼 종료시 메뉴로 화면전환
    //{
    //   SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
    //    tutorial_end = false;
    //}


    /// <summary>
    /// 게임 라운드 진행 함수
    /// 라운드 진행방식은 양손 협응력 테스트와 동일
    private void Stage1()
    {
        if (Maze_UI.playtime <= 0 && isStage1 == true) //1라운드 시간종료시 이동
        {
            timeout_count++;
            round1_end = true;
            isStage2 = true;
        }
        if (round1_end == true) isStage2 = true;
    }

    private void Stage2() //1라운드 종료시 캐릭터 이동
    {
        isStage1 = false;
        if (round1_end == true) //라운드 2이동시 위치, 방향설정
        {
            float y = 0f;
            float x = 0f;
            float z = 0f;
            transform.position = new Vector3(45.855f, 0.494f, -4.557f);
            transform.rotation = Quaternion.Euler(x, y, z);
            Maze_UI.playtime = 30;
            Camera1 = true;
            round1_end = false;
        }
        if (round2_end == true) isStage3 = true;

        if (Maze_UI.playtime <= 0 && isStage2 == true)
        {
            timeout_count++;
            round2_end = true;
            isStage3 = true;
        }
    }

    private void Stage3() //2라운드 종료시 캐릭터 이동
    {
        isStage2 = false;
        maze_result = true;
        if (round2_end == true)//3라운드 이동시 위치, 방향설정
        {
            float y = 90f;
            float x = 0f;
            float z = 0f;
            transform.position = new Vector3(85.88f, 0.494f, 3.086f);
            transform.rotation = Quaternion.Euler(x, y, z);
            Maze_UI.playtime = 45;
            Camera2 = true;
            round2_end = false;
        }
        if (Maze_UI.playtime <= 0 && isStage3 == true)
        {
            timeout_count++;
            SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
            round3_end = false;
            isStage1 = true;
        }
        if (round3_end == true)
        {
            SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
            round3_end = false;
            isStage1 = true;
        }
    }
    /// </summary>

}