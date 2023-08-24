using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Mazemove_tuto : MonoBehaviour
{
    public float CH_speed; //캐릳터 이동속도
    Vector3 lookDirection;

    public static int Tutorial_Count;
    public static bool tutorial_end; // 튜토리얼 종료확인
    // Start is called before the first frame update
    void Start()
    {
        CH_speed = 3f;
        Tutorial_Count = 0;
    }

    // Update is called once per frame
    void Update()
    {
        move();
        if (tutorial_end == true) Tutorial(); //튜토리얼 종료함수
    }

    private void move() //캐릭터 제어
    {
        if (Input.GetKey(KeyCode.UpArrow) || Input.GetKey(KeyCode.DownArrow) ||
            Input.GetKey(KeyCode.LeftArrow) || Input.GetKey(KeyCode.RightArrow))
        {

            float xx = Input.GetAxisRaw("Vertical");
            float zz = Input.GetAxisRaw("Horizontal");
            lookDirection = xx * Vector3.forward + zz * Vector3.right;

            this.transform.rotation = Quaternion.LookRotation(lookDirection);
            this.transform.Translate(Vector3.forward * CH_speed * Time.deltaTime);
        }
    }

    private void Tutorial() //튜토리얼 종료시 메뉴로 화면전환
    {
        SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
        tutorial_end = false;
    }

    private void OnTriggerEnter(Collider other) //충돌 감지
    {
        if (other.tag == "tutorial_trigger") Tutorial_Count++; //튜토리얼 충돌 카운트
        if (other.tag == "tuto_end") tutorial_end = true;
    }
}
